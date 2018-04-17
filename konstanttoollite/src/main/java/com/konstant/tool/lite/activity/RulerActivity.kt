package com.konstant.tool.lite.activity

import android.os.Bundle
import android.view.WindowManager
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.view.KonstantRulerView


class RulerActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(KonstantRulerView(this))
    }
}
