package com.konstant.develop

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setFullScreenStatusBarLightMode(this)
        setContentView(R.layout.activity_second)

        btn_hide_status_bar.setOnClickListener {
            StatusBarUtil.showStatusBar(this,false)
        }

        btn_show_status_bar.setOnClickListener {
            StatusBarUtil.showStatusBar(this,true)
        }

        btn_hide_navigation_bar.setOnClickListener {

        }

        btn_show_navigation_bar.setOnClickListener {
            val option = window.decorView.systemUiVisibility
            val option1 = option or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = option1
        }
    }
}