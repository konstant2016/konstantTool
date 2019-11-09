package com.konstant.tool.lite.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konstant.tool.lite.R
import com.konstant.tool.lite.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.activity_anim_exit)
        finish()
    }
}
