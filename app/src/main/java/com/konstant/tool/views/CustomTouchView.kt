package com.konstant.tool.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by konstant on 2018/3/22.
 */

class CustomTouchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint = Paint()
    private var num = 3

    init {
        mPaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (num) {
            2 -> {
                canvas.drawCircle(100f, 100f, 50f, mPaint)
                canvas.drawCircle(200f, 100f, 50f, mPaint)
            }
            3 -> {
                canvas.drawCircle(100f, 100f, 50f, mPaint)
                canvas.drawCircle(200f, 100f, 50f, mPaint)
                canvas.drawCircle(300f, 100f, 50f, mPaint)
            }
        }
    }


    fun setTOW() {
        num = 2
        invalidate()
    }

    fun setThree() {
        num = 3
        invalidate()
    }

    fun setRed() {
        mPaint.color = Color.RED
        invalidate()
    }

    fun setYellow() {
        mPaint.color = Color.YELLOW
        invalidate()
    }
}
