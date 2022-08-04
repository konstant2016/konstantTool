package com.konstant.tool.lite.view

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object WindowInsertHelper {

    /**
     * 设置是否沉浸状态栏
     * fit：true 沉浸
     */
    fun setFitSystemBar(window: Window, fit: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, fit)
    }

    /**
     * 设置状态栏图标颜色
     */
    fun setStatusBarColor(window: Window, isWhite: Boolean) {
        val content = window.findViewById<View>(android.R.id.content) ?: return
        val controller = ViewCompat.getWindowInsetsController(content) ?: return
        controller.isAppearanceLightStatusBars = isWhite
    }

    /**
     * 设置状态栏的背景颜色
     */
    fun setStatusBackgroundColor(window: Window, color: Int) {
        window.statusBarColor = color
    }

    /**
     * 设置导航栏的背景颜色
     */
    fun setNavigationBackgroundColor(window: Window, color: Int) {
        window.navigationBarColor = color
    }

    /**
     * 设置导航栏的图标颜色
     */
    fun setNavigationBarColor(window: Window, isWhite: Boolean) {
        val content = window.findViewById<View>(android.R.id.content) ?: return
        val controller = ViewCompat.getWindowInsetsController(content) ?: return
        controller.isAppearanceLightNavigationBars = isWhite
    }

    /**
     * 设置全屏
     */
    fun setFullScreen(window: Window, full: Boolean) {
        val controller = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        if (full) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, true)
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}