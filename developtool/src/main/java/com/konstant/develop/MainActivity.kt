package com.konstant.develop

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBaseViews()
    }

    private fun initBaseViews() {
        btn_teacher.setOnClickListener {
            openApp("com.yangcong345.teacher")
        }
        setting_teacher.setOnClickListener {
            openSetting("com.yangcong345.teacher")
        }
        btn_public.setOnClickListener {
            openApp("com.yangcong345.android.phone")
        }
        setting_public.setOnClickListener {
            openSetting("com.yangcong345.android.phone")
        }
        btn_scheme.setOnClickListener {
            if (TextUtils.isEmpty(et_scheme.text)) return@setOnClickListener
            try {
                val s = et_scheme.text.toString()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(s))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "没有应用可以响应SCHEME", Toast.LENGTH_LONG).show()
            }
        }
        btn_test.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

    private fun openApp(packageName: String) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
        }
    }

    private fun openSetting(packageName: String) {
        try {
            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
        }
    }
}