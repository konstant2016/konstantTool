package com.yangcong345.kratos.render.view

import android.content.Context
import java.lang.IllegalArgumentException

object KTViewFactory {

    private val viewTypeMap = mapOf(
        "KTFlexLayout" to KTFlexLayout::class.java,
        "KTFrameLayout" to KTFrameLayout::class.java,
        "KTLabel" to KTLabel::class.java,
        "KTImageView" to KTImageView::class.java
    )

    fun createView(context: Context, viewType: String): KTView {
        val clazz = viewTypeMap[viewType] ?: throw IllegalArgumentException("不支持的视图类型")
        val constructor = clazz.getConstructor(Context::class.java)
        return constructor.newInstance(context) as KTView
    }

}