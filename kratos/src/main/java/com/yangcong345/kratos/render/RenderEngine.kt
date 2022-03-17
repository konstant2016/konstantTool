package com.yangcong345.kratos.render

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yangcong345.kratos.render.view.KTView
import com.yangcong345.kratos.render.view.KTViewFactory
import com.yangcong345.kratos.render.view.KTViewGroup
import java.util.concurrent.Executors

/**
 * 时间：2022/2/22 6:24 下午
 * 作者：吕卡
 * 备注：渲染引擎，用来根据JSON渲染出来对应的页面，在子线程渲染对应的页面数据
 */

class RenderEngine private constructor() {

    companion object {
        fun createRenderEngine(): RenderEngine {
            return RenderEngine()
        }
    }

    private var mRootView: View? = null
    private var mClickListener: ViewEventListener? = null
    private val mThreadHandler by lazy { Handler(Looper.getMainLooper()) }

    /**
     * 添加点击回调
     */
    fun setEventListener(listener: ViewEventListener) {
        mClickListener = listener
    }

    /**
     * 构建View
     */
    fun buildView(context: Context, json: String, result: (View) -> Unit) {
        Executors.newCachedThreadPool().execute {
            mRootView = parseJson(context, json, object : ViewEventListener {
                override fun onViewClicked(viewId: String) {
                    mClickListener?.onViewClicked(viewId)
                }
            })
            mThreadHandler.post { result.invoke(mRootView!!) }
        }
    }

    /**
     * 批量更新view数据
     */
    fun updateView(data: String) {
        val type = object : TypeToken<List<RenderStyle>>() {}.type
        val renderStyleList = Gson().fromJson<List<RenderStyle>>(data, type)
        for (renderStyle in renderStyleList) {
            updateView(renderStyle)
        }
    }

    private fun updateView(viewStyle: RenderStyle) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            mThreadHandler.post { updateViewInner(viewStyle) }
        } else {
            updateViewInner(viewStyle)
        }
    }

    /**
     * 根据ViewId更新数据
     */
    private fun updateViewInner(renderStyle: RenderStyle) {
        val hashCode = renderStyle.viewId.hashCode()
        val view = mRootView?.findViewById<View>(hashCode) ?: return
        if (view is KTView) {
            view.updateViewStyle(renderStyle)
        }
    }

    /**
     * 解析根布局
     */
    private fun parseJson(context: Context, json: String, controller: ViewEventListener): View {
        val style = Gson().fromJson(json, RenderStyle::class.java)
        if (TextUtils.equals(style.viewType, "KTFlexLayout") || TextUtils.equals(style.viewType, "KTFrameLayout")) {
            val rootView = KTViewFactory.createView(context, style.viewType ?: "")
            rootView.setController(controller)
            rootView.updateViewStyle(style)
            addChildrenIntoParent(rootView as KTViewGroup, style, controller)
            return rootView as View
        }
        throw IllegalArgumentException("跟布局的viewType必须为KTFlexLayout或者KTFrameLayout")
    }

    private fun addChildrenIntoParent(parent: KTViewGroup, style: RenderStyle, controller: ViewEventListener) {
        style.children?.forEach { data ->
            val view = KTViewFactory.createView((parent as View).context, data.viewType ?: "")
            view.setController(controller)
            parent.insertView(view)
            view.updateViewStyle(data)
            if (view is KTViewGroup) {
                addChildrenIntoParent(view, data, controller)
            }
        }
    }
}


