package com.konstant.tool.lite.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PermissionRequester : AppCompatActivity() {

    private val REQUEST_CODE = 123

    companion object {

        private var mGrantedCallback: ((List<String>) -> Unit)? = null
        private var mDefinedCallback: ((List<String>) -> Unit)? = null

        fun requestPermission(context: Context, permissions: List<String>, grantedCallback: (List<String>) -> Unit, definedCallback: (List<String>) -> Unit) {
            mGrantedCallback = grantedCallback;mDefinedCallback = definedCallback
            with(Intent(context, PermissionRequester::class.java)) {
                putExtra("permission", permissions.toTypedArray())
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}