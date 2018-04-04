package com.konstant.toollite.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.eventbusparam.SwipeBackState
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_layout.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("菜籽工具箱-轻量版")
        setSwipeBackEnable(false)
        initBaseViews()

        layout_translate.setOnClickListener { startActivity(Intent(this@MainActivity,TranslateActivity::class.java)) }

        layout_beauty.setOnClickListener { startActivity(Intent(this@MainActivity,BeautyActivity::class.java)) }

        layout_compass.setOnClickListener { startActivity(Intent(this@MainActivity,CompassActivity::class.java)) }
    }

    override fun initBaseViews() {
        img_back.visibility = View.GONE
        img_more.setOnClickListener { startActivity(Intent(this,SettingActivity::class.java)) }
    }

    override fun SwipeBackChanged(msg: SwipeBackState) {

    }

}
