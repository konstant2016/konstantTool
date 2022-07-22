package com.konstant.develop.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.ViewGroup
import com.konstant.develop.R
import com.konstant.develop.utils.SizeUtil

class ShadowYouSheTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : YousheTextView(context, attributeSet, defStyle) {

    private var aroundShadowWidth = 1
    private var aroundShadowColor = Color.WHITE
    private val strokePaint by lazy { backgroundText.paint }
    private val backgroundText by lazy { YousheTextView(context, attributeSet, defStyle) }

    init {
        val attribute = context.obtainStyledAttributes(attributeSet, R.styleable.ShadowTextView)
        aroundShadowColor = attribute.getColor(R.styleable.ShadowTextView_shadowAroundColor, Color.WHITE)
        aroundShadowWidth = attribute.getInteger(R.styleable.ShadowTextView_shadowAroundRadio, 1)
        attribute.recycle()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        this.postInvalidate()
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        backgroundText.layoutParams = params
        super.setLayoutParams(params)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!TextUtils.equals(backgroundText.text, this.text)) {
            backgroundText.text = text
            this.postInvalidate()
        }
        backgroundText.measure(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        backgroundText.layout(left, top, right, bottom)
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        drawBackgroundText(canvas)
        super.onDraw(canvas)
    }

    private fun drawBackgroundText(canvas: Canvas?) {
        strokePaint.textSize = paint.textSize
        strokePaint.typeface = paint.typeface
        strokePaint.flags = paint.flags
        strokePaint.alpha = paint.alpha
        strokePaint.style = Paint.Style.STROKE
        strokePaint.color = aroundShadowColor
        strokePaint.strokeWidth = SizeUtil(context).resetValue(aroundShadowWidth.toFloat())
        val string = text.toString()
        canvas?.drawText(string, (width - strokePaint.measureText(string)) / 2, baseline.toFloat(), strokePaint)
    }

}