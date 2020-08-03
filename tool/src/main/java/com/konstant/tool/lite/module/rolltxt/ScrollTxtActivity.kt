package com.konstant.tool.lite.module.rolltxt

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scroll_txt.*

class ScrollTxtActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_txt)
        showStatusBar(false)
        showTitleBar(false)
        txt.text = intent.getStringExtra("txt")
    }
}
