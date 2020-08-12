package com.konstant.tool.lite.module.skip

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.os.Handler
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.NotificationCreator
import kotlinx.android.synthetic.main.activity_auto_skip.view.*

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
        when (event.eventType) {
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED,
            AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED -> {
                Handler().postDelayed({
                    val selfResult = exclusiveSelf(event)
                    if (selfResult) return@postDelayed
                    val customRuleResult = handleCustomRules(event)
                    if (customRuleResult) return@postDelayed
                    val whiteListResult = handleWhiteList(event)
                    if (whiteListResult) return@postDelayed
                    handleMatch()
                }, 1000)
            }
        }
    }

    // 排除自身应用
    private fun exclusiveSelf(event: AccessibilityEvent): Boolean {
        if (event.packageName == packageName) return true
        if (rootInActiveWindow?.packageName == packageName) return true
        return false
    }

    // 自定义规则
    private fun handleCustomRules(event: AccessibilityEvent): Boolean {
        val find = mCustomRules.find {
            (it.packageName == event.packageName || it.packageName == rootInActiveWindow.packageName) }
        if (find != null) {
            val nodeInfoList = rootInActiveWindow?.findAccessibilityNodeInfosByViewId(find.resourceId)
            if (nodeInfoList.isNullOrEmpty()) return false
            for (info in nodeInfoList) {
                val result = info.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                if (result) return true
            }
        }
        return false
    }

    // 应用白名单
    private fun handleWhiteList(event: AccessibilityEvent): Boolean {
        val whiteList = AutoSkipManager.getAppWhiteList()
        return whiteList.any { it == event.packageName || it == rootInActiveWindow.packageName }
    }

    // 模糊匹配
    private fun handleMatch() {
        if (!AutoSkipManager.getMatch(this)) return
        val nodeInfoList = rootInActiveWindow?.findAccessibilityNodeInfosByText("跳过")
                ?: rootInActiveWindow?.findAccessibilityNodeInfosByText("关闭") ?: return
        for (nodeInfo in nodeInfoList) {
            nodeInfo.text ?: continue
            if (!nodeInfo.isEnabled) continue
            val contains = nodeInfo.text.contains("跳过") || nodeInfo.text.contains("关闭")
            if (contains && AutoSkipManager.getMatch(this)) {
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
            Toast.makeText(this, getString(R.string.skip_describe), Toast.LENGTH_SHORT).show()
        }
    }
}
