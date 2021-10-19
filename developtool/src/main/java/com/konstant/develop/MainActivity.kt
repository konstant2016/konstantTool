package com.konstant.develop

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        btn_student.setOnClickListener {
            openApp("com.yangcong345.onionschool")
        }
        setting_student.setOnClickListener {
            openSetting("com.yangcong345.onionschool")
        }
        btn_middle.setOnClickListener {
            openApp("com.yangcong345.android.middle2")
        }
        setting_middle.setOnClickListener {
            openSetting("com.yangcong345.android.middle2")
        }
        btn_public.setOnClickListener {
            openApp("com.yangcong345.android.phone")
        }
        setting_public.setOnClickListener {
            openSetting("com.yangcong345.android.phone")
        }
        btn_setting.setOnClickListener {
            openApp("com.android.settings")
        }
        btn_wifi.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivity(intent)
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
        layout_parent.setOnClickListener {
            hideSoftKeyboard()
        }
        btn_pad.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
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
            val packageIntent = packageManager.getLaunchIntentForPackage(packageName)
            if (packageIntent == null){
                Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
                return
            }

            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "找不到应用", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.icon_qr_scan){
            startActivity(Intent(this, QRScanActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    // 隐藏软键盘
    private fun hideSoftKeyboard() {
        if (window.attributes.softInputMode ==
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        if (currentFocus == null) {
            return
        }
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }
}