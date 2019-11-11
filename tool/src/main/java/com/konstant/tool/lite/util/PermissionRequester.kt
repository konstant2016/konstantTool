package com.konstant.tool.lite.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class PermissionRequester : AppCompatActivity() {

    private val REQUEST_CODE = 123

    companion object {

        private var mGrantedCallback: ((List<String>) -> Unit)? = null
        private var mDefinedCallback: ((List<String>) -> Unit)? = null

        fun requestPermission(context: Context, permissions: List<String>, grantedCallback: (List<String>) -> Unit, deniedCallback: (List<String>) -> Unit) {
            mGrantedCallback = grantedCallback;mDefinedCallback = deniedCallback
            with(Intent(context, PermissionRequester::class.java)) {
                putExtra("permission", permissions.toTypedArray())
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        invasionStatusBar(this)
        val permissions = intent.getStringArrayExtra("permission")
        if (permissions.isNullOrEmpty()) {
            finish();return
        }
        if (Build.VERSION_CODES.M >= Build.VERSION.SDK_INT) {
            mGrantedCallback?.invoke(permissions.toList())
            finish();return
        }
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE) return
        val grantedList = ArrayList<String>()
        val definedList = ArrayList<String>()
        grantResults.forEachIndexed { index, state ->
            if (state == PackageManager.PERMISSION_GRANTED) {
                grantedList.add(permissions[index])
            } else {
                definedList.add(permissions[index])
            }
        }
        if (grantedList.isNotEmpty()) mGrantedCallback?.invoke(grantedList)
        if (definedList.isNotEmpty()) mDefinedCallback?.invoke(definedList)
        finish()
    }

    // 隐藏系统的状态栏 以及 任务栏
    private fun invasionStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            val decorView = window.decorView
            decorView.systemUiVisibility = (decorView.systemUiVisibility
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}