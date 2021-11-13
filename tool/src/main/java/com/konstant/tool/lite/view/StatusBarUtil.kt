package com.konstant.tool.lite.view

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

/**
 * 时间：6/18/21 2:24 PM
 * 作者：吕卡
 * 备注：状态栏和底部导航栏的处理工具
 */

object StatusBarUtil {

    /**
     * 设置全屏，状态栏白色
     */
    fun setFullScreenWhite(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 隐藏导航栏
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = Color.TRANSPARENT
            }
        }
    }

    /**
     * 设置全屏，状态栏图标黑色
     */
    fun setFullScreenBlack(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        } else {
            window.statusBarColor = Color.BLACK
        }
    }

    /**
     * 设置导航栏颜色
     */
    fun setNavigationBarColor(window: Window, color: Int) {
        window.navigationBarColor = color
    }

    /**
     * 设置全屏，不显示状态栏
     */
    fun setFullScreen(activity: AppCompatActivity) {
        val window = activity.window
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 刘海屏延伸到状态栏
     */
    fun setCutoutMode(activity: AppCompatActivity) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= 28) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }
    }
}