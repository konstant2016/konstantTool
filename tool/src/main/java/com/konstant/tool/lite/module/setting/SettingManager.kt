package com.konstant.tool.lite.module.setting

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.setting.param.SwipeBackStatus
import com.konstant.tool.lite.util.FileUtil
import org.greenrobot.eventbus.EventBus
import java.io.File

/**
 * 描述:保存用户设置
 * 创建人:菜籽
 * 创建时间:2018/4/26 下午7:18
 * 备注:
 */


object SettingManager {

    private val NAME_SELECTED_THEME = "selectedTheme"
    private val NAME_SWIPEBACK_STATUS = "swipeBackStatus"
    private val EXIT_TIPS_STATUS = "exitTipsStatus"
    private val KILL_PROCESS_STATUS = "killProcessStatus"
    private val ADAPTER_DARK_MODE = "adapterDarkMode"
    val NAME_USER_HEADER = "header_big.jpg"

    fun saveTheme(context: Context, theme: Int) {
        FileUtil.saveDataToSp(context, NAME_SELECTED_THEME, theme)
    }

    fun setSwipeBackStatus(context: Context, state: Int) {
        FileUtil.saveDataToSp(context, NAME_SWIPEBACK_STATUS, state)
    }

    fun getSwipeBackStatus(context: Context) =
            FileUtil.readDataFromSp(context, NAME_SWIPEBACK_STATUS, 0)

    fun deleteUserHeaderThumb(context: Context) {
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), NAME_USER_HEADER).delete()
    }

    fun setAdapterDarkMode(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, ADAPTER_DARK_MODE, status)
    }

    fun getAdapterDarkMode(context: Context) = FileUtil.readDataFromSp(context, ADAPTER_DARK_MODE, true)

    /**
     * 判断当前用户是否已开启系统暗黑模式适配
     * 如果未开启：则返回用户自己设置的主题样式
     * 如果已开启：判断当前系统是否为暗黑模式
     *             如果是：则返回暗黑模式
     *             否则：返回用户自定义样式
     */
    fun getTheme(context: Context): Int {
        if (!getAdapterDarkMode(context)) {
            return FileUtil.readDataFromSp(context, NAME_SELECTED_THEME, R.style.tool_lite_class)
        }
        if (getDarkModeStatus(context)) {
            return R.style.tool_lite_dark
        }
        return FileUtil.readDataFromSp(context, NAME_SELECTED_THEME, R.style.tool_lite_class)
    }

    fun getDarkModeStatus(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    fun getUserHeaderThumb(context: Context): Bitmap {
        val path = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}${File.separator}$NAME_USER_HEADER"
        return if (File(path).exists()) {
            BitmapFactory.decodeFile(path)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
        }
    }

    // 读取是否退出提示：true->显示，false->不显示
    fun getExitTipsStatus(context: Context) = FileUtil.readDataFromSp(context, EXIT_TIPS_STATUS, true)

    fun setExitTipsStatus(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, EXIT_TIPS_STATUS, status)
    }

    fun getKillProcess(context: Context) = FileUtil.readDataFromSp(context, KILL_PROCESS_STATUS, false)

    fun setKillProcess(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, KILL_PROCESS_STATUS, status)
    }

}