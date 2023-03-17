package com.konstant.develop.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object WindowInsertHelper {

    /**
     * 设置侵入到状态栏和导航栏
     * invadeStatusBar：true 侵入到状态栏，false不侵入
     * invadeNavigationBar：true 侵入到导航栏，false不侵入
     */
    fun setInvadeSystemBar(activity: Activity, invadeStatusBar: Boolean, invadeNavigationBar: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
        val window = activity.window
        val topPadding = if (invadeStatusBar) {
            setStatusBarBackgroundColor(window, Color.TRANSPARENT)
            0
        } else {
            getStatusBarHeight(window)
        }
        val bottomPadding = if (invadeNavigationBar) {
            setNavigationBarBackgroundColor(window,Color.TRANSPARENT)
            0
        } else {
            getNavigationBarHeight(window)
        }
        val content = activity.findViewById<View>(android.R.id.content) ?: return
        content.post {
            content.setPadding(0, topPadding, 0, bottomPadding)
        }
    }

    /**
     * 已验证
     * 设置状态栏图标颜色
     * iconLight：图标是否为白色
     */
    fun setStatusBarLight(window: Window, iconLight: Boolean) {
        val controller = WindowCompat.getInsetsController(window, window.decorView) ?: return
        controller.isAppearanceLightStatusBars = !iconLight
    }

    /**
     * 已验证
     * 设置状态栏背景色
     * 状态栏没有设置为透明的时候才会生效
     */
    fun setStatusBarBackgroundColor(window: Window, color: Int) {
        window.statusBarColor = color
    }

    /**
     * 已验证
     * 设置导航栏背景色
     */
    fun setNavigationBarBackgroundColor(window: Window, color: Int) {
        window.navigationBarColor = color
    }

    /**
     * 已验证
     * 设置导航栏的图标颜色
     * Android13上改为自动适配了
     * 当导航栏背景为深色时，图标自动为白色
     * 当导航栏背景为亮色时，图标自动变为黑色
     */
    fun setNavigationBarLight(window: Window, isWhite: Boolean) {
        val controller = WindowCompat.getInsetsController(window, window.decorView) ?: return
        controller.isAppearanceLightNavigationBars = isWhite
    }

    /**
     * 已验证
     * 显示状态栏和导航栏
     */
    fun showWindowControl(window: Window, showStatus: Boolean, showNavigation: Boolean) {
        val controller = WindowCompat.getInsetsController(window, window.decorView) ?: return
        if (showStatus) {
            controller.show(WindowInsetsCompat.Type.statusBars())
        } else {
            controller.hide(WindowInsetsCompat.Type.statusBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        if (showNavigation) {
            controller.show(WindowInsetsCompat.Type.navigationBars())
        } else {
            controller.hide(WindowInsetsCompat.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


    /**
     * 已验证
     * 获取状态栏高度
     * 状态栏显示时，才会返回正常值，不显示时，返回高度为0
     */
    fun getStatusBarHeight(window: Window): Int {
        val content = window.findViewById<View>(android.R.id.content)
        val insetsCompat = ViewCompat.getRootWindowInsets(content)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
    }

    /**
     * 已验证
     * 获取导航栏的高度
     * 导航栏显示时，才会返回正常值，不显示时，返回高度为0
     */
    fun getNavigationBarHeight(window: Window): Int {
        val content = window.findViewById<View>(android.R.id.content)
        val insetsCompat = ViewCompat.getRootWindowInsets(content)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }
}