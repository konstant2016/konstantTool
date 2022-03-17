package com.yangcong345.kratos.render.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yangcong345.kratos.render.view.compoent.BaseAttributeImpl
import com.yangcong345.kratos.render.view.compoent.BaseViewStyle
import com.yangcong345.kratos.render.RenderStyle
import com.yangcong345.kratos.render.view.common.layout.ShapeFrameLayout
import com.yangcong345.kratos.render.view.compoent.BaseAttribute

/**
 * 帧布局，对应layoutType为Absolute的情况
 *
 * context:用来创建控件
 * viewStyle：用来描述自身的样式
 * layoutStyle：用来摆放子控件的排布方式，对于KTFrameLayout来说，它没有这个属性
 */

class KTFrameLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ShapeFrameLayout(context, attrs, defStyle), KTViewGroup, BaseAttribute by BaseAttributeImpl() {

    init {
        onCreate(this)
    }

    override fun updateViewStyle(style: String) {
        val response = Gson().fromJson(style, RenderStyle::class.java)
        updateViewStyle(response)
    }

    /**
     * 外部通知刷新属性时，会用到这个方法
     */
    override fun updateViewStyle(style: RenderStyle) {
        val viewStyle = style.getViewStyle(BaseViewStyle::class.java)
        updateBaseAttribute(shapeDrawableBuilder, style.viewId, viewStyle)
        updateLayoutStyle(style.layoutStyle, layoutParams ?: LayoutParams(0, 0))
        updateActions(style.viewAction)
    }

    override fun insertView(view: KTView) {
        val layoutParams = LayoutParams(0, 0)
        super.addView(view as View, layoutParams)
    }

    @Deprecated(replaceWith = ReplaceWith("使用insertView(view: View)"), message = "使用推荐的方法，可以帮你添加LayoutParams")
    override fun addView(view: View) {
        super.addView(view)
    }

    @Deprecated(replaceWith = ReplaceWith("使用addView(view: View)"), message = "使用推荐的方法，可以帮你添加LayoutParams")
    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
    }

}