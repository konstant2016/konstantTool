package com.yangcong345.kratos.render.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.yangcong345.kratos.render.view.compoent.BaseAttributeImpl
import com.yangcong345.kratos.render.view.compoent.BaseViewStyle
import com.yangcong345.kratos.render.RenderStyle
import com.yangcong345.kratos.render.view.common.view.ShapeImageView
import com.yangcong345.kratos.render.view.compoent.BaseAttribute

/**
 * 时间：2022/3/3 4:00 下午
 * 作者：吕卡
 * 备注：layoutParams 指的是父控件的layoutParams
 */
class KTImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ShapeImageView(context, attrs, defStyle), KTView, BaseAttribute by BaseAttributeImpl() {

    init {
        onCreate(this)
    }

    override fun updateViewStyle(style: String) {
        val data = Gson().fromJson(style, RenderStyle::class.java)
        updateViewStyle(data)
    }

    override fun updateViewStyle(style: RenderStyle) {
        val viewStyle = style.getViewStyle(ViewStyle::class.java)
        updateLayoutStyle(style.layoutStyle, layoutParams)
        updateBaseAttribute(shapeDrawableBuilder, style.viewId, viewStyle)
        updateInnerAttribute(viewStyle)
    }

    /**
     * 更新自身的属性
     */
    private fun updateInnerAttribute(viewStyle: ViewStyle?) {
        viewStyle ?: return
        if (!TextUtils.isEmpty(viewStyle.contentMode)) {
            scaleType = when (viewStyle.contentMode) {
                "scaleToFill" -> ScaleType.FIT_XY
                "scaleAspectFit" -> ScaleType.CENTER_INSIDE
                "scaleAspectFill" -> ScaleType.CENTER_CROP
                else -> ScaleType.CENTER_CROP
            }
        }
        Handler(Looper.getMainLooper()).post {
            Glide.with(context).load(viewStyle.image).into(this)
        }
    }

    class ViewStyle(
        var image: String? = "",
        var contentMode: String? = ""
    ) : BaseViewStyle()

}