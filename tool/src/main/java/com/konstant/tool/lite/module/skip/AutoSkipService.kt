package com.konstant.tool.lite.module.skip

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.konstant.tool.lite.base.NotificationCreator

/**
 * 描述：
 * 创建者：吕卡
 * 时间：2020/7/23:2:30 PM
 */
class AutoSkipService : AccessibilityService() {

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
            val nodeInfoList = rootInActiveWindow?.findAccessibilityNodeInfosByText("跳过") ?: return@postDelayed
            for (nodeInfo in nodeInfoList) {
                nodeInfo.text ?: continue
                if (nodeInfo.text.contains("跳过")) {
                    val performAction = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    if (!performAction){
                        nodeInfo.parent?.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }
            }
        }, 500)
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
