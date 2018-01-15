package com.konstant.konstanttools.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.PermissionChecker.checkSelfPermission
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager

/**
 * 描述:所有fragment的父类，用于封装一下常用方法
 * 创建人:菜籽
 * 创建时间:2018/1/10 上午11:08
 * 备注:
 */

open class BaseFragment : Fragment() {

    private val mRequestCode = 12
    private lateinit var mReason: String
    private lateinit var mPermission: String
    protected lateinit var mActivity: Activity

    private var isCreated = false
    private var mIsVisibleToUser = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreated = true
        if (mIsVisibleToUser) {
            onFragmentResume()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
        if (isVisibleToUser and isCreated) {
            onFragmentResume()
        } else {
            onFragmentPause()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    open protected fun onFragmentResume() {

    }

    open protected fun onFragmentPause() {

    }

    open protected fun isFragmentResume(): Boolean = isCreated and mIsVisibleToUser

    // 申请权限
    protected fun requestPermission(permission: String, reason: String) {
        mReason = reason
        mPermission = permission
        // 判断自身是否拥有此权限
        if (PackageManager.PERMISSION_DENIED == checkSelfPermission(mActivity, permission)) {
            // 如果没有，就去申请权限
            requestPermissions(arrayOf(permission), mRequestCode)
        } else {
            // 如果有，则返回给子类调用
            onPermissionResult(true)
        }
    }

    // 系统activity回调的权限申请结果
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == mRequestCode && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            // 权限申请成功
            onPermissionResult(true)
        } else {
            // 权限申请失败，判断是否需要弹窗解释原因
            if (shouldShowRequestPermissionRationale(permissions[0])) {
                // 如果需要弹窗，则弹窗解释原因
                showReasonDialog()
            } else {
                // 否则，告诉子类权限申请失败
                onPermissionResult(false)
            }
        }
    }

    // 展示给用户说明权限申请原因
    private fun showReasonDialog() {
        AlertDialog.Builder(mActivity)
                .setMessage(mReason)
                .setNegativeButton("取消", { dialog, _ ->
                    dialog.dismiss()
                    onPermissionResult(false)
                })
                .setPositiveButton("确定", { dialog, _ ->
                    dialog.dismiss()
                    requestPermissions(arrayOf(mPermission), mRequestCode)
                })
                .create().show()
    }

    // 需要子类实现的权限申请结果，不写成接口是因为并不是所有的activity都需要申请权限
    open protected fun onPermissionResult(result: Boolean) {

    }

    // 隐藏软键盘
    protected fun hideSoftKeyboard() {
        if (mActivity?.window?.attributes?.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            return
        }
        (mActivity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                ?.hideSoftInputFromWindow(mActivity?.currentFocus?.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS)
    }
}