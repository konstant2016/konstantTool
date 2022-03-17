package com.yangcong345.kratos.render.view

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import com.google.gson.Gson
import com.yangcong345.kratos.render.view.compoent.BaseAttributeImpl
import com.yangcong345.kratos.render.view.compoent.BaseViewStyle
import com.yangcong345.kratos.render.RenderStyle
import com.yangcong345.kratos.render.view.common.view.ShapeTextView
import com.yangcong345.kratos.render.view.compoent.BaseAttribute

class KTLabel @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ShapeTextView(context, attrs, defStyle), KTView, BaseAttribute by BaseAttributeImpl() {

    init {
        onCreate(this)
        setDefaultAttribute()
    }

    override fun updateViewStyle(style: String) {
        val renderStyle = Gson().fromJson(style, RenderStyle::class.java)
        updateViewStyle(renderStyle)
    }

    /**
     * 刷新UI属性
     */
    override fun updateViewStyle(renderStyle: RenderStyle) {
        val viewStyle = renderStyle.getViewStyle(ViewStyle::class.java)
        updateBaseAttribute(shapeDrawableBuilder, renderStyle.viewId, viewStyle)
        updateLayoutStyle(renderStyle.layoutStyle, layoutParams)
        updateInnerLabel(viewStyle)
        updateActions(renderStyle.viewAction)
    }

    /**
     * 设置私有属性
     */
    private fun updateInnerLabel(viewStyle: ViewStyle?) {
        viewStyle ?: return
        includeFontPadding = false
        if (viewStyle.fontSize ?: 0f > 0f) {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, viewStyle.fontSize!!)
        }
        if (!TextUtils.isEmpty(viewStyle.textColor)) {
            setTextColor(Color.parseColor(viewStyle.textColor))
        }
        if (!TextUtils.isEmpty(viewStyle.textAlignment)) {
            gravity = when (viewStyle.textAlignment) {
                "left" -> Gravity.START
                "center" -> Gravity.CENTER
                "right" -> Gravity.END
                else -> Gravity.START
            }
        }
        if (!TextUtils.isEmpty(viewStyle.lineBreakMode)) {
            ellipsize = when (viewStyle.lineBreakMode) {
                "normal" -> null
                "head" -> TextUtils.TruncateAt.START
                "tail" -> TextUtils.TruncateAt.END
                "middle" -> TextUtils.TruncateAt.MIDDLE
                else -> null
            }
        }
        text = viewStyle.text
    }

    private fun setDefaultAttribute() {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, 25f)
        gravity = Gravity.CENTER
    }

    class ViewStyle(
        var text: String? = "",
        var fontSize: Float? = 0f,
        var textColor: String? = "",
        var textAlignment: String? = "",
        var lineBreakMode: String? = "",
    ) : BaseViewStyle()
}