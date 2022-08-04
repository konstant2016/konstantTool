package com.konstant.develop.utils

import android.graphics.Color
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object WindowInsertHelper {

    /**
     * 设置沉浸到状态栏里面，也就是状态栏透明，内容延伸到状态栏里面
     * 如果为true，则系统会直接在activity的跟布局中添加一个topPadding，把内容顶下来
     * 一般用不到这个属性
     */
    fun setFitWindow(window: Window, fit: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, fit)
    }

    /**
     * 设置状态栏图标颜色
     * iconLight：图标是否为白色
     */
    fun setStatusBarLight(window: Window, iconLight: Boolean) {
        val content = window.findViewById<View>(android.R.id.content) ?: return
        val controller = ViewCompat.getWindowInsetsController(content) ?: return
        controller.isAppearanceLightStatusBars = !iconLight
    }

    /**
     * 设置状态栏背景色
     */
    fun setStatusBarBackgroundColor(window: Window, color: Int) {
        window.statusBarColor = color
    }

    /**
     * 设置导航栏背景色
     */
    fun setNavigationBarBackgroundColor(window: Window, color: Int) {
        window.navigationBarColor = color
    }


    /**
     * 是否绘制到状态栏里面
     */
    fun drawIntoStatusBar(window: Window, into: Boolean) {
        if (into) {
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.statusBarColor = Color.BLACK
        }
    }

    /**
     * 隐藏状态栏和导航栏
     */
    fun hideWindowControl(window: Window, hideStatus: Boolean, hideNavigation: Boolean) {
        val controller = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        if (hideStatus) {
            controller.hide(WindowInsetsCompat.Type.systemBars())
        } else {
            controller.show(WindowInsetsCompat.Type.systemBars())
        }
        if (hideNavigation) {
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            WindowCompat.setDecorFitsSystemWindows(window, true)
        }
    }

    /**
     * ********************************************************
     */

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


    /**
     * 获取状态栏高度
     */
    fun getStatusBarHeight(window: Window): Int {
        val content = window.findViewById<View>(android.R.id.content)
        val insetsCompat = ViewCompat.getRootWindowInsets(content)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.statusBars()).top
    }

    /**
     * 获取导航栏的高度
     */
    fun getNavigationBarHeight(window: Window): Int {
        val content = window.findViewById<View>(android.R.id.content)
        val insetsCompat = ViewCompat.getRootWindowInsets(content)
        val windowInsetsCompat = insetsCompat ?: return 0
        return windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
    }
}