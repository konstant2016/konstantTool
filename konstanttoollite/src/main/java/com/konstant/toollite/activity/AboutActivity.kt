package com.konstant.toollite.activity

import android.os.Bundle
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setTitle("关于")
    }
}
