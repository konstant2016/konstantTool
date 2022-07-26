package com.konstant.develop.tree

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.konstant.develop.R

class StarLayoutView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    val layoutParams = LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT).apply { weight = 1f }

    init {
        orientation = HORIZONTAL
    }

    fun setTotalCount(count: Int) {
        removeAllViews()
        for (i in 0 until count) {
            val imageView = ImageView(context)
            addView(imageView, layoutParams)
        }
    }

    fun setStarCount(count: Int) {
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            if (view is ImageView) {
                if (i < count) {
                    view.setImageResource(R.drawable.arena_star_full)
                } else {
                    view.setImageResource(R.drawable.arena_star_empty)
                }
            }
        }
    }

}