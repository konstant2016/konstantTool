package com.konstant.tool.lite.module.skip

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.konstant.tool.lite.base.NotificationCreator

/**
 * 描述：
 * 创建者：吕卡
 * 时间：2020/7/23:2:30 PM
 */
class AutoSkipService : AccessibilityService() {

    private val mCustomRules by lazy { AutoSkipManager.getCustomRules() }

    override fun onServiceConnected() {
        super.onServiceConnected()
        val config = AccessibilityServiceInfo()
        config.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or AccessibilityEvent.TYPE_VIEW_CLICKED
        config.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
        config.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
        serviceInfo = config
        val notification = NotificationCreator.createForegroundNotification(this, "")
        startForeground(1, notification)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        if (event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return
        val packageName = event.packageName.toString()
        val className = event.className.toString()
        tryGetActivity(packageName, className) ?: return
        Handler().postDelayed({
            val customRuleResult = handleCustomRules(packageName, className)
            if (customRuleResult) return@postDelayed
            val whiteListResult = handleWhiteList(packageName)
            if (whiteListResult) return@postDelayed
            handleMatch()
        }, 500)
    }

    // 自定义规则
    private fun handleCustomRules(packageName: String, className: String): Boolean {
        val find = mCustomRules.find { it.packageName == packageName && it.className == className }
        if (find != null) {
            val nodeInfoList = rootInActiveWindow?.findAccessibilityNodeInfosByViewId(find.resourceId)
                    ?: return false
            for (info in nodeInfoList) {
                val result = info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                if (result) return true
            }
        }
        return false
    }

    // 应用白名单
    private fun handleWhiteList(packageName: String): Boolean {
        val whiteList = AutoSkipManager.getAppWhiteList()
        return whiteList.any { it == packageName }
    }

    // 模糊匹配
    private fun handleMatch() {
        if (!AutoSkipManager.getMatch(this)) return
        val nodeInfoList = rootInActiveWindow?.findAccessibilityNodeInfosByText("跳过") ?: return
        for (nodeInfo in nodeInfoList) {
            nodeInfo.text ?: continue
            if (nodeInfo.text.contains("跳过") && AutoSkipManager.getMatch(this)) {
                val performAction = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                if (performAction) {
                    showSkipToast()
                }
                if (!performAction) {
                    val result = nodeInfo.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    if (result == true) {
                        showSkipToast()
                    }
                }
            }
        }
    }

    private fun showSkipToast() {
        if (AutoSkipManager.getShowToast(this)) {
            Toast.makeText(this, "自动跳过", Toast.LENGTH_SHORT).show()
        }
    }

    private fun tryGetActivity(packageName: String, className: String): ActivityInfo? {
        val componentName = ComponentName(packageName, className)
        return try {
            packageManager.getActivityInfo(componentName, 0);
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }

}
