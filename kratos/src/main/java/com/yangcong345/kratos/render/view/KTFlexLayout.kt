package com.yangcong345.kratos.render.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.yangcong345.kratos.render.view.compoent.BaseAttributeImpl
import com.yangcong345.kratos.render.view.compoent.BaseViewStyle
import com.yangcong345.kratos.render.LayoutStyle
import com.yangcong345.kratos.render.PARAM_UN_KNOW
import com.yangcong345.kratos.render.RenderStyle
import com.yangcong345.kratos.render.view.common.layout.ShapeFlexboxLayout
import com.yangcong345.kratos.render.view.compoent.BaseAttribute

/**
 * 弹性布局，对应layoutType为KTFlexLayout的情况
 *
 * context:用来创建控件
 * viewStyle：用来描述自身的样式
 * layoutStyle：用来摆放子控件的排布方式
 */

class KTFlexLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ShapeFlexboxLayout(context, attrs, defStyle), KTViewGroup, BaseAttribute by BaseAttributeImpl() {

    init {
        onCreate(this)
    }

    override fun updateViewStyle(style: String) {
        val data = Gson().fromJson(style, RenderStyle::class.java)
        updateViewStyle(data)
    }

    /**
     * 外部通知刷新属性时，会用到这个方法
     */
    override fun updateViewStyle(renderStyle: RenderStyle) {
        val layoutStyle = renderStyle.layoutStyle
        val viewStyle = renderStyle.getViewStyle(BaseViewStyle::class.java)
        updateBaseAttribute(shapeDrawableBuilder, renderStyle.viewId, viewStyle)
        updateLayoutStyle(layoutStyle, layoutParams ?: LayoutParams(0, 0))
        updateInnerAttribute(layoutStyle)
        updateActions(renderStyle.viewAction)
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

    /**
     * 设置ViewGroup自身的属性
     */
    private fun updateInnerAttribute(layoutStyle: LayoutStyle?) {
        layoutStyle ?: return
        if (layoutStyle.getFlexDirection() != PARAM_UN_KNOW) {
            flexDirection = layoutStyle.getFlexDirection()
        }
        if (layoutStyle.getFlexWrap() != PARAM_UN_KNOW) {
            flexWrap = layoutStyle.getFlexWrap()
        }
        if (layoutStyle.getJustifyContent() != PARAM_UN_KNOW) {
            justifyContent = layoutStyle.getJustifyContent()
        }
        if (layoutStyle.getAlignItems() != PARAM_UN_KNOW) {
            alignItems = layoutStyle.getAlignItems()
        }
        if (layoutStyle.getAlignContent() != PARAM_UN_KNOW) {
            alignContent = layoutStyle.getAlignContent()
        }
    }
}