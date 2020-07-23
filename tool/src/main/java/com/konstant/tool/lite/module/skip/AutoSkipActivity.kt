package com.konstant.tool.lite.module.skip

import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity

/**
 * 描述：自动跳过开屏广告
 * 创建者：吕卡
 * 时间：2020/7/23:6:02 PM
 */
class AutoSkipActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_skip)
        val intent = Intent(this, AutoSkipService::class.java)
        startService(intent)
    }

}