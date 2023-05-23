package com.yangcong345.installhelper

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * 时间：2023/5/17 17:39
 * 作者：吕卡
 * 备注：自动点击安装的服务
 */

class AutoInstallService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        val notification = NotificationCreator.createForegroundNotification(this)
        startForeground(1, notification)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        val node = rootInActiveWindow ?: return
        Log.d("AutoInstallService", "" + Build.BRAND + ",," + event.packageName)
        when (Build.BRAND.lowercase()) {
            "xiaomi", "redmi" -> {
                dispatchXiaoMiDevice(node, event)
            }
            "oppo" -> {
                dispatchOppo(node, event)
            }
            "vivo" -> {
                dispatchVivo(node, event)
            }
        }
    }

    private fun clickView(rootNode: AccessibilityNodeInfo) {
        val list = mutableListOf<AccessibilityNodeInfo>()
        list.addAll(rootNode.findAccessibilityNodeInfosByText("重新安装"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("继续安装"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("继续更新"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("验证并安装"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("打开应用"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("允许本次安装"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("以后都允许"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("继续"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("打开"))
        list.addAll(rootNode.findAccessibilityNodeInfosByText("允许"))

        list.forEach {
            it.parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
    }

    override fun onInterrupt() {

    }

    private fun dispatchXiaoMiDevice(rootNode: AccessibilityNodeInfo, event: AccessibilityEvent) {
        when (event.packageName) {
            "com.miui.packageinstaller",
            "com.android.systemui" -> {
                clickView(rootNode)
            }
        }
    }

    private fun dispatchVivo(rootNode: AccessibilityNodeInfo, event: AccessibilityEvent) {
        when (event.packageName) {
            "com.android.packageinstaller",
            "com.vivo.upslide",
            "com.android.systemui"
            -> {
                clickView(rootNode)
            }
        }
    }

    private fun dispatchOppo(rootNode: AccessibilityNodeInfo, event: AccessibilityEvent) {
        when (event.packageName) {
            "com.oplus.appdetail",
            "com.android.packageinstaller",
            "com.oplus.notificationmanager" -> {
                insertPassword(rootNode)
                clickView(rootNode)
            }
        }
    }

    private fun insertPassword(rootNode: AccessibilityNodeInfo) {
        val editText = rootNode.findFocus(AccessibilityNodeInfo.FOCUS_INPUT) ?: return
        Log.d("AutoInstallService", "" + editText.packageName + editText.className)
        if (editText.packageName == "com.oplus.appdetail" && editText.className.equals("android.widget.EditText")) {
            val arguement = Bundle()
            arguement.putCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "Onion345")
            editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguement)
        }
    }

}