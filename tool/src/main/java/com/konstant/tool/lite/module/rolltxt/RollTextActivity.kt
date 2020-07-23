package com.konstant.tool.lite.module.rolltxt

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_roll_text.*

/**
 * 时间：2019/5/17 17:33
 * 创建：吕卡
 * 描述：滚动文字
 */

class RollTextActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roll_text)
        setTitle(getString(R.string.scroll_title))
        btn_create.setOnClickListener { onBtnPressed() }
    }

    private fun onBtnPressed() {
        if (TextUtils.isEmpty(et_input.text)) {
            showToast(getString(R.string.base_input_empty_toast))
            return
        }
        with(Intent(this, ScrollTxtActivity::class.java)) {
            putExtra("txt", et_input.text.toString())
            startActivity(this)
        }
    }
}
