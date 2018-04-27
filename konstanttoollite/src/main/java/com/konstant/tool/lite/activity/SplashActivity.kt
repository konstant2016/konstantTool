package com.konstant.tool.lite.activity

import android.content.Intent
import android.os.Bundle
import com.konstant.tool.lite.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
