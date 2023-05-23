package com.konstant.tool.lite.util

import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowManager
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
    fun setInvadeSystemBar(window: Window, invadeStatusBar: Boolean, invadeNavigationBar: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val topPadding = if (invadeStatusBar) {
            setStatusBarBackgroundColor(window, Color.TRANSPARENT)
            0
        } else {
            getStatusBarHeight(window)
        }
        val bottomPadding = if (invadeNavigationBar) {
            setNavigationBarBackgroundColor(window, Color.TRANSPARENT)
            0
        } else {
            getNavigationBarHeight(window)
        }
        val content = window.findViewById<View>(android.R.id.content) ?: return
        content.post {
            content.setPadding(0, topPadding, 0, bottomPadding)
        }
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
     * autoConfigIcon：是否根据背景颜色自动调节虚拟按钮的颜色
     * 注意：color不能设置为全透明，因此还需要额外处理
     */
    fun setNavigationBarBackgroundColor(window: Window, color: Int, autoConfigIcon: Boolean = false) {
        if (color == Color.TRANSPARENT){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        window.navigationBarColor = color
        if (autoConfigIcon) {
            val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
            setNavigationBarLight(window, darkness < 0.5)
        }
    }

    /**
     * 已验证
     * 设置导航栏的图标颜色
     * Android13上改为自动适配了
     * 当导航栏背景为深色时，图标自动为白色
     * 当导航栏背景为亮色时，图标自动变为黑色
     */
    fun setNavigationBarLight(window: Window, iconLight: Boolean) {
        val controller = WindowCompat.getInsetsController(window, window.decorView) ?: return
        controller.isAppearanceLightNavigationBars = !iconLight
    }


    /**
     * 已验证
     * 获取状态栏高度
     * 状态栏显示时，才会返回正常值，不显示时，返回高度为0
     */
    fun getStatusBarHeight(window: Window): Int {
        val insetsCompat = ViewCompat.getRootWindowInsets(window.decorView)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
    }


    /**
     * 已验证
     * 获取导航栏的高度
     * 导航栏显示时，才会返回正常值，不显示时，返回高度为0
     */
    fun getNavigationBarHeight(window: Window): Int {
        val insetsCompat = ViewCompat.getRootWindowInsets(window.decorView)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }

}