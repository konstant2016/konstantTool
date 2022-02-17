package com.konstant.develop

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.lang.reflect.Method

/**
 * 刘海屏工具
 */
object NotchModeUtil {

    /**
     * 获取刘海高度
     * 刘海都是处于状态栏的为止，因此，只要判断出存在刘海，则直接返回状态栏的高度即可
     * return px
     */
    fun getNotchHeight(activity: Activity): Int {
        if (hasNotchWithDefault(activity)) {
            return getStatusBarHeight(activity)
        }
        if (hasNotchAtHuawei(activity)) {
            return getStatusBarHeight(activity)
        }
        if (hasNotchAtOPPO(activity)) {
            return getStatusBarHeight(activity)
        }
        if (hasNotchAtVivo(activity)) {
            return getStatusBarHeight(activity)
        }
        if (hasNotchAtXiaoMi(activity)) {
            return getStatusBarHeight(activity)
        }
        return 0
    }

    /**
     * 对view做刘海屏适配，左右两侧各添加一个状态栏宽度的margin值
     * views:此界面中有多个view需要进行刘海屏适配
     */
    fun adapterNotchMode(activity: Activity, views: List<View>) {
        val notchHeight = getNotchHeight(activity)
        if (notchHeight == 0) return
        views.forEach { view ->
            val params = view.layoutParams
            if (params is ViewGroup.MarginLayoutParams) {
                params.setMargins(notchHeight, 0, notchHeight, 0)
            }
            view.layoutParams = params
        }
    }

    /**
     * 设置dialog适配到刘海屏中
     */
    fun setDialogFullScreen(dialog: DialogFragment) {
        val window = dialog.dialog?.window ?: return
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.setPadding(0, 0, 0, 0)
        window.decorView.setBackgroundColor(Color.WHITE)
        val layoutParams: WindowManager.LayoutParams = window.attributes
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // 延伸显示区域到刘海
            val lp: WindowManager.LayoutParams = window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = lp
            // 设置页面全屏显示
            val decorView: View = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
        window.attributes = layoutParams
    }
    
    /**
     * 获取状态栏高度
     *
     * @return px
     */
    private fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = (context.resources.displayMetrics.density * 25).toInt()
        //获取status_bar_height资源的ID
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    /**
     * 刘海屏延伸到状态栏
     */
    fun setNotchMode(activity: AppCompatActivity) {
        val window = activity.window ?: return
        if (Build.VERSION.SDK_INT >= 28) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }
    }

    /**
     * VIVO刘海屏判断
     * @return
     */
    private fun hasNotchAtVivo(context: Context): Boolean {
        val contain = Build.MANUFACTURER?.toLowerCase()?.contains("vivo") ?: false
        if (!contain) return false
        val VIVO_NOTCH = 0x00000020
        return try {
            val classLoader = context.classLoader
            val FtFeature = classLoader.loadClass("android.util.FtFeature")
            val method: Method = FtFeature.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            method.invoke(FtFeature, VIVO_NOTCH) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 华为刘海屏判断
     * @return
     */
    private fun hasNotchAtHuawei(context: Context): Boolean {
        val contain = Build.MANUFACTURER?.toLowerCase()?.contains("huawei") ?: false
        if (!contain) return false
        return try {
            val classLoader = context.classLoader
            val HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get: Method = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: Exception) {
            false
        }
    }

    /**
     * OPPO刘海屏判断
     * @return
     */
    private fun hasNotchAtOPPO(context: Context): Boolean {
        val contain = Build.MANUFACTURER?.toLowerCase()?.contains("oppo") ?: false
        if (!contain) return false
        return context.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }


    /**
     * 小米刘海屏判断
     */
    private fun hasNotchAtXiaoMi(context: Context): Boolean {
        val contain = Build.MANUFACTURER?.toLowerCase()?.contains("xiaomi") ?: false
        if (!contain) return false
        return try {
            val cl = context.classLoader
            val SystemProperties = cl.loadClass("android.os.SystemProperties")
            val get = SystemProperties.getMethod("getInt", String::class.java, Int::class.javaPrimitiveType)
            get.invoke(SystemProperties, "ro.miui.notch", 0) as Int == 1
        } catch (e: java.lang.Exception) {
            return false
        }
    }

    /**
     * 官方的刘海屏判断方法
     */
    private fun hasNotchWithDefault(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= 28) {
            return activity.window.decorView.rootWindowInsets?.displayCutout?.boundingRects?.isNotEmpty()
                    ?: false
        }
        return false
    }

}