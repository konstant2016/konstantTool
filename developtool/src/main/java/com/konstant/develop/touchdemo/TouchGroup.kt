package com.konstant.develop.touchdemo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.RelativeLayout

class TouchGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(
    context, attrs, defStyleAttr) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val a = ev?:return super.dispatchTouchEvent(ev)
        Log.d("TouchGroup",""+a.action)
        when(a.action){
            MotionEvent.ACTION_DOWN ->{
                Log.d("TouchGroup","按下")
            }
            MotionEvent.ACTION_UP ->{
                Log.d("TouchGroup","抬起")
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}