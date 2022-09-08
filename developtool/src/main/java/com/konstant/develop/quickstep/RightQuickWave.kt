package com.konstant.develop.quickstep

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_quick_right.view.*

class RightQuickWave @JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAtr: Int = 0)
    : LinearLayout(context, attr, defStyleAtr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_quick_right, this, true)
    }

    fun startAnimation() {
        val animation = AnimationUtils.loadAnimation(context, R.anim.animation_quick_right)
        layout_animation.startAnimation(animation)
        lottie_view.playAnimation()
    }

    fun stopAnimation() {
        layout_animation.clearAnimation()
        lottie_view.cancelAnimation()
    }

}