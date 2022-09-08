package com.konstant.develop.quickstep

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import com.konstant.develop.R
import com.konstant.develop.dp2px
import kotlinx.android.synthetic.main.layout_quick_left.view.*

/**
 * 时间：2022/8/23 17:19
 * 作者：吕卡
 * 备注：左侧的波浪
 */

class LeftQuickWave @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAtr: Int = 0)
    : LinearLayout(context, attr, defStyleAtr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_quick_left, this, true)
    }

    fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.animation_quick_left)
        layout_animation.startAnimation(animation)
        lottie_view.playAnimation()
    }

    fun stopAnimation() {
        layout_animation.clearAnimation()
        lottie_view.cancelAnimation()
    }

}