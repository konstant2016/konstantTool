package com.konstant.tool.lite.util

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics


/**
 * 修改系统默认dpi的工具
 * 来自今日头条的适配方案
 */

object Density {

    private var mAppDensity: Float = 0f
    private var mAppScaleDensity: Float = 0f

    private const val SIGN_DPI_WIDTH = 360   // 设计图中的宽度
    private const val SIGN_DPI_HEIGHT = 640  // 设计图中的高度

    fun init(application: Application) {
        with(application.resources.displayMetrics) {
            mAppDensity = density
            mAppScaleDensity = scaledDensity
        }

        registerFontChanged(application)
        registerActivityCreated(application)
    }

    // 此方法用于跟随用户设置的系统字体大小变化
    private fun registerFontChanged(application: Application) {
        application.registerComponentCallbacks(object : ComponentCallbacks {

            override fun onConfigurationChanged(newConfig: Configuration?) {
                if (newConfig != null && newConfig.fontScale > 0) {
                    mAppScaleDensity = application.resources.displayMetrics.scaledDensity
                }
            }

            override fun onLowMemory() {

            }

        })
    }

    // 监听activity的生命周期
    private fun registerActivityCreated(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.let { setActivityDensity(it) }
            }

            override fun onActivityStarted(activity: Activity?) {}

            override fun onActivityDestroyed(activity: Activity?) {}

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

            override fun onActivityStopped(activity: Activity?) {}
        })
    }

    // 当activity被创建时，使用自定的dpi
    private fun setActivityDensity(activity: Activity) {
        val metrics = getDisplayMetrics(activity)
        val displayMetrics = activity.resources.displayMetrics
        displayMetrics.density = metrics.density
        displayMetrics.scaledDensity = metrics.scaledDensity
        displayMetrics.densityDpi = metrics.densityDpi
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val widthPixels = context.resources.displayMetrics.widthPixels
        val heightPixels = context.resources.displayMetrics.heightPixels
        val targetDensity = if (heightPixels > widthPixels) {
            widthPixels / SIGN_DPI_WIDTH
        } else {
            heightPixels / SIGN_DPI_WIDTH
        }

        val targetScaleDensity = targetDensity * (mAppScaleDensity / mAppDensity)
        val targetDensityDpi = targetScaleDensity * 160

        val displayMetrics = context.resources.displayMetrics
        displayMetrics.density = targetDensity.toFloat()
        displayMetrics.scaledDensity = targetScaleDensity
        displayMetrics.densityDpi = targetDensityDpi.toInt()
        return displayMetrics
    }

}