package com.konstant.tool.lite.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.konstant.tool.lite.R
import com.konstant.tool.lite.main.MainActivity
import com.konstant.tool.lite.util.FileUtil
import com.tencent.bugly.crashreport.CrashReport

class SplashActivity : AppCompatActivity() {

    companion object{
        const val SHOW_DIALOG_KEY = "showSplashDialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val show = FileUtil.readDataFromSp(this, SHOW_DIALOG_KEY, true)
        if (!show) {
            jumpToMain()
            return
        }
        AlertDialog.Builder(this)
                .setTitle(R.string.base_tips)
                .setMessage(R.string.setting_about_describe)
                .setPositiveButton(R.string.base_agree) { _, _ ->
                    CrashReport.initCrashReport(applicationContext,"b3cb53863c",true)
                    jumpToMain()
                    FileUtil.saveDataToSp(this, SHOW_DIALOG_KEY, false)
                    finish()
                }
                .setNegativeButton(R.string.base_reject) { _, _ -> finish() }
                .create().show()
    }

    private fun jumpToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.activity_anim_exit)
        finish()
    }
}
