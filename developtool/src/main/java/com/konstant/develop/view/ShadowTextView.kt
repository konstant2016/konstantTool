package com.konstant.develop.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class ShadowTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : AppCompatTextView(context, attributeSet, defStyle) {

    private var aroundShadowWidth = 2
    private var aroundShadowColor = Color.RED
    private val mPaint by lazy { Paint() }

    fun setShadowColor(color: Int) {
        this.aroundShadowColor = color
        invalidate()
    }

    fun setShadowWidth(width: Int) {
        this.aroundShadowWidth = width
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        drawTextShadow(canvas)
        super.onDraw(canvas)
    }

    private fun drawTextShadow(canvas: Canvas) {
        val mPaint = paint
        mPaint.isAntiAlias = true
        mPaint.color = aroundShadowColor
        val text = text.toString()
        val startX = layout.getLineLeft(0)
        val startY = baseline.toFloat()
        canvas.drawText(text, startX + aroundShadowWidth, startY, mPaint)
        canvas.drawText(text, startX, startY - aroundShadowWidth, mPaint)
        canvas.drawText(text, startX, startY + aroundShadowWidth, mPaint)
        canvas.drawText(text, startX - aroundShadowWidth, startY, mPaint)
    }
}