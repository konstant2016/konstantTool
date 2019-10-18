package com.konstant.tool.tools

import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.util.Log

/**
 * 描述:权限请求工具类
 * 创建人:菜籽
 * 创建时间:2018/1/2 下午3:41
 * 备注:
 */

object PermissionRequest : Activity() {

    private lateinit var mCallback: (result: Boolean) -> Unit
    private val mRequestCode = 12

    @Synchronized
    fun permissionRequest(activity: Activity, permission: String, reason: String, callback: (result: Boolean) -> Unit) {
        mCallback = callback
        // 判断自身是否有此权限
        if (PackageManager.PERMISSION_DENIED == ActivityCompat.checkSelfPermission(activity, permission)) {
            // 如果没有，判断是否需要向用户解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // 如果需要解释，则弹窗解释
                showDialog(activity, permission, reason)
            } else {
                // 如果不需要，则直接申请权限
                requestPermission(activity, permission)
            }
        } else {
            // 如果有，则回调true
            callback(true)
        }

    }


    private fun requestPermission(activity: Activity, permission: String) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), mRequestCode)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i("权限申请结果", "requestCode:${requestCode},grantResults[0]:${grantResults[0]}")
        if (requestCode == mRequestCode && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            mCallback(true)
        } else {
            mCallback(false)
        }
    }

    private fun showDialog(activity: Activity, permission: String, reason: String) {
        AlertDialog.Builder(activity)
                .setMessage(reason)
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                    mCallback(false)
                }
                .setPositiveButton("确定") { dialog, _ ->
                    dialog.dismiss()
                    requestPermission(activity, permission)
                }.create().show()
    }
}