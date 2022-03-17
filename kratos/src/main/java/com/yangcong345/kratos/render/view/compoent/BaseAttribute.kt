package com.yangcong345.kratos.render.view.compoent

import android.view.View
import android.view.ViewGroup
import com.yangcong345.kratos.render.LayoutStyle
import com.yangcong345.kratos.render.view.common.builder.ShapeDrawableBuilder
import com.yangcong345.kratos.render.ViewEventListener

interface BaseAttribute {

    /**
     * 初始化，保存一些属性
     */
    fun onCreate(view: View)

    /**
     * 设置控制器
     */
    fun setController(controller: ViewEventListener)

    /**
     * 设置通用属性
     */
    fun updateBaseAttribute(drawableBuilder: ShapeDrawableBuilder, viewId: String, viewStyle: BaseViewStyle?)

    /**
     * 设置布局属性
     * 控件相对于父控件的布局属性
     */
    fun updateLayoutStyle(layoutStyle: LayoutStyle?, layoutParams: ViewGroup.LayoutParams)

    /**
     * 响应点击事件
     */
    fun updateActions(actions: List<String>?)

}