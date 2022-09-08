package com.konstant.develop.quickstep

import android.os.Bundle
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import com.konstant.develop.R
import com.konstant.develop.StatusBarUtil
import kotlinx.android.synthetic.main.activity_quick_step.*

class QuickStepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_step)
        StatusBarUtil.setFullScreenStatusBarDarkMode(this)
        updateView()
    }

    private fun updateView() {
        layout_left.startAnimation()
        layout_right.startAnimation()
    }

}