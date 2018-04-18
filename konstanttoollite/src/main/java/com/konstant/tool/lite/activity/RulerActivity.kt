package com.konstant.tool.lite.activity

import android.os.Bundle
import android.view.WindowManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity


class RulerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ruler)
        setSwipeBackEnable(false)
    }
}
