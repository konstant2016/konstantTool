package com.konstant.tool.ui.activity.testactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.konstant.tool.R
import kotlinx.android.synthetic.main.title_layout.*

class DrawerlayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawerlayout)
        supportActionBar!!.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)   // 透明状态栏
        img_back.setOnClickListener { finish() }
    }
}
