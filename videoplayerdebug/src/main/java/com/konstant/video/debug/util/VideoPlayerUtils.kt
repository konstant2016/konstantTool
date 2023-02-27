package com.konstant.video.debug.util

import android.os.Build
import android.view.View
import android.view.Window


/**
 * 非公共的 Util，仅供播放器使用
 */
object VideoPlayerUtils {

    /**
     * 适配全面屏
     * 去除全面屏下 "手势提示线"（可在小米手机设置中搜索得到）
     */
    fun adaptToFullScreen(window: Window?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window?.decorView?.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

}