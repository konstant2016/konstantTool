package com.yangcong345.installhelper

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.WindowInsets
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.yangcong345.install.helper.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = resources.getColor(R.color.purple_200)
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()
        val enable = isAccessibilityEnable(this)
        if (!enable) {
            btn_enable.text = "点击开启辅助权限"
            btn_enable.setOnClickListener {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }
        } else {
            btn_enable.text = "辅助权限已开启"
            btn_enable.setOnClickListener {
                Toast.makeText(this, "辅助权限已开启", Toast.LENGTH_LONG).show()
            }
        }
    }

    // 系统辅助功能是否已开启
    private fun isAccessibilityEnable(context: Context): Boolean {
        val service = context.packageName.toString() + "/" + AutoInstallService::class.java.canonicalName
        val accessibilityEnabled = try {
            Settings.Secure.getInt(context.applicationContext.contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
        } catch (e: Settings.SettingNotFoundException) {
            0
        }
        val mStringColonSplitter: TextUtils.SimpleStringSplitter = TextUtils.SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue)
                while (mStringColonSplitter.hasNext()) {
                    val accessibilityService: String = mStringColonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

}