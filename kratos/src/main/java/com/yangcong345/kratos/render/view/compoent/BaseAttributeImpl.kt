package com.yangcong345.kratos.render.view.compoent

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.google.android.flexbox.FlexboxLayout
import com.yangcong345.kratos.render.LayoutStyle
import com.yangcong345.kratos.render.PARAM_UN_KNOW
import com.yangcong345.kratos.render.view.common.builder.ShapeDrawableBuilder
import com.yangcong345.kratos.render.ViewEventListener

class BaseAttributeImpl : BaseAttribute {

    private var mView: View? = null
    private var mViewId = ""
    private val mActions = mutableListOf<String>()
    private var mController: ViewEventListener? = null

    override fun onCreate(view: View) {
        mView = view
        view.setOnClickListener {
            if (mActions.any { it == "CLICK" }) {
                mController?.onViewClicked(mViewId)
            }
        }
    }

    override fun setController(controller: ViewEventListener) {
        mController = controller
    }

    override fun updateBaseAttribute(drawableBuilder: ShapeDrawableBuilder, viewId: String, viewStyle: BaseViewStyle?) {
        if (!TextUtils.isEmpty(viewId)) {
            mViewId = viewId
            mView?.id = viewId.hashCode()
        }
        viewStyle ?: return
        if (viewStyle.alpha ?: 1f != 1f) {
            mView?.alpha = viewStyle.alpha!!
        }
        if (!TextUtils.isEmpty(viewStyle.backgroundColor)) {
            drawableBuilder.solidColor = Color.parseColor(viewStyle.backgroundColor)
        }
        if (!TextUtils.isEmpty(viewStyle.borderColor)) {
            drawableBuilder.strokeColor = Color.parseColor(viewStyle.borderColor)
        }
        if (viewStyle.borderWidth ?: 0f > 0f) {
            drawableBuilder.strokeWidth = viewStyle.borderWidth!!
        }
        if (viewStyle.cornerRadius ?: 0f > 0f) {
            drawableBuilder.setRadius(viewStyle.cornerRadius ?: 0f)
        }
        drawableBuilder.intoBackground()
    }

    override fun updateLayoutStyle(layoutStyle: LayoutStyle?, layoutParams: ViewGroup.LayoutParams) {
        val view = mView ?: return
        layoutStyle ?: return
        setSize(mView!!.context, layoutParams, layoutStyle)

        setMargins(layoutParams, layoutStyle)
        setPaddings(layoutStyle)

        // 流式布局会添加这几个属性
        if (layoutParams is FlexboxLayout.LayoutParams) {
            buildFlexAttribute(layoutParams, layoutStyle)
        }

        // 帧布局会添加gravity属性
        if (layoutParams is FrameLayout.LayoutParams) {
            buildFrameAttribute(layoutParams, layoutStyle)
        }

        view.layoutParams = layoutParams
    }

    override fun updateActions(actions: List<String>?) {
        actions?.let {
            mActions.clear()
            mActions.addAll(it)
        }
    }

    /**
     * 宽高都没值时，则不设置宽高
     * 宽度有值，设置宽度
     * 高度有值，设置高度
     * 宽度有值，高度没值，宽高比有值，则计算宽高比然后设置
     */
    private fun setSize(context: Context, layoutParams: ViewGroup.LayoutParams, layoutStyle: LayoutStyle) {
        if (TextUtils.isEmpty(layoutStyle.layoutWidth) && TextUtils.isEmpty(layoutStyle.layoutHeight)) return
        if (!TextUtils.isEmpty(layoutStyle.layoutWidth)) {
            val width = getPhysicalSize(context, layoutStyle.layoutWidth)
            layoutParams.width = width
        }
        if (!TextUtils.isEmpty(layoutStyle.layoutHeight)) {
            val height = getPhysicalSize(context, layoutStyle.layoutHeight)
            layoutParams.height = height
            return
        }
        if (!TextUtils.isEmpty(layoutStyle.layoutWidth) && layoutStyle.aspectRatio != null) {
            val width = getPhysicalSize(context, layoutStyle.layoutWidth)
            val height = (width / layoutStyle.aspectRatio).toInt()
            layoutParams.width = width
            layoutParams.height = height
        }
    }

    // 设置外边距
    private fun setMargins(layoutParams: ViewGroup.LayoutParams, layoutStyle: LayoutStyle) {
        val view = mView ?: return
        val margin = layoutStyle.margin ?: return
        val left = getPhysicalSize(view.context, margin.left)
        val top = getPhysicalSize(view.context, margin.top)
        val right = getPhysicalSize(view.context, margin.right)
        val bottom = getPhysicalSize(view.context, margin.bottom)
        (layoutParams as ViewGroup.MarginLayoutParams).setMargins(left, top, right, bottom)
    }

    // 设置内边距
    private fun setPaddings(layoutStyle: LayoutStyle) {
        val view = mView ?: return
        val padding = layoutStyle.padding ?: return
        val left = getPhysicalSize(view.context, padding.left)
        val top = getPhysicalSize(view.context, padding.top)
        val right = getPhysicalSize(view.context, padding.right)
        val bottom = getPhysicalSize(view.context, padding.bottom)
        view.setPadding(left, top, right, bottom)
    }

    // 设置Flex的相关属性
    private fun buildFlexAttribute(layoutParams: FlexboxLayout.LayoutParams, layoutStyle: LayoutStyle) {
        layoutStyle.flexShrink?.let {
            layoutParams.flexShrink = it
        }
        layoutStyle.flexGrow?.let {
            layoutParams.flexGrow = it
        }
        layoutStyle.flexBasisPercent?.let {
            layoutParams.flexBasisPercent = it
        }
        if (layoutStyle.getAlignSelf() != PARAM_UN_KNOW) {
            layoutParams.alignSelf = layoutStyle.getAlignSelf()
        }
    }

    // 设置Frame的相关属性
    private fun buildFrameAttribute(layoutParams: FrameLayout.LayoutParams, layoutStyle: LayoutStyle) {
        val margin = layoutStyle.margin ?: return
        when {
            // 左上角
            !TextUtils.isEmpty(margin.left) && !TextUtils.isEmpty(margin.top) -> {
                layoutParams.gravity = Gravity.START or Gravity.TOP
            }
            // 右上角
            !TextUtils.isEmpty(margin.right) && !TextUtils.isEmpty(margin.top) -> {
                layoutParams.gravity = Gravity.END or Gravity.TOP
            }
            // 左下角
            !TextUtils.isEmpty(margin.left) && !TextUtils.isEmpty(margin.bottom) -> {
                layoutParams.gravity = Gravity.START or Gravity.BOTTOM
            }
            // 右下角
            !TextUtils.isEmpty(margin.right) && !TextUtils.isEmpty(margin.bottom) -> {
                layoutParams.gravity = Gravity.END or Gravity.BOTTOM
            }
            // 居左
            !TextUtils.isEmpty(margin.left) -> {
                layoutParams.gravity = Gravity.START
            }
            // 居上
            !TextUtils.isEmpty(margin.top) -> {
                layoutParams.gravity = Gravity.TOP
            }
            // 居右
            !TextUtils.isEmpty(margin.right) -> {
                layoutParams.gravity = Gravity.END
            }
            // 居下
            !TextUtils.isEmpty(margin.bottom) -> {
                layoutParams.gravity = Gravity.BOTTOM
            }
        }
    }

    private fun getPhysicalSize(context: Context, widthString: String): Int {
        if (TextUtils.isEmpty(widthString)) return 0
        val size = if (widthString.contains("%")) {
            val width = widthString.replace("%", "").toFloat() / 100
            val screenWidth = getScreenWidth(context)
            (width * screenWidth).toInt()
        } else {
            widthString.replace("%", "").toInt()
        }
        if (size == -1) return ViewGroup.LayoutParams.WRAP_CONTENT
        return size
    }

    private fun getScreenWidth(context: Context): Int {
        val metric = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }
}