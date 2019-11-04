package com.konstant.tool.lite.module.setting

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import com.konstant.tool.lite.R
import com.konstant.tool.lite.util.FileUtil
import java.io.File

/**
 * 描述:保存用户设置
 * 创建人:菜籽
 * 创建时间:2018/4/26 下午7:18
 * 备注:
 */


object SettingManager {

    private const val NAME_SELECTED_THEME = "selectedTheme"
    private const val NAME_WALLPAPER_TRANSPARENT = "wallpaperTransparent"
    private const val NAME_AUTO_UPDATE = "autoUpdate"
    private const val NAME_SWIPEBACK_STATUS = "swipeBackStatus"
    private const val NAME_BROWSER_TYPE = "browserStatus"
    private const val EXIT_TIPS_STATUS = "exitTipsStatus"
    private const val KILL_PROCESS_STATUS = "killProcessStatus"
    private const val ADAPTER_DARK_MODE = "adapterDarkMode"
    const val NAME_USER_HEADER = "header_big.jpg"

    // 保存用户选择的主题
    fun saveTheme(context: Context, theme: Int) {
        FileUtil.saveDataToSp(context, NAME_SELECTED_THEME, theme)
    }

    // 保存滑动返回的状态
    fun setSwipeBackStatus(context: Context, state: Int) {
        FileUtil.saveDataToSp(context, NAME_SWIPEBACK_STATUS, state)
    }

    // 获取滑动返回的状态
    fun getSwipeBackStatus(context: Context) =
            FileUtil.readDataFromSp(context, NAME_SWIPEBACK_STATUS, 0)

    // 删除保存的用户头像（恢复默认）
    fun deleteUserHeaderThumb(context: Context) {
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), NAME_USER_HEADER).delete()
    }

    // 适配系统暗黑模式
    fun setAdapterDarkMode(context: Context, status: Boolean) {
        Log.d("SettingManager", "黑暗模式：$status")
        FileUtil.saveDataToSp(context, ADAPTER_DARK_MODE, status)
    }

    // 读取：是否开启适配暗黑模式
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

    // 判断当前是否系统是否处于暗黑模式
    fun getDarkModeStatus(context: Context): Boolean {
        val mode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return mode == Configuration.UI_MODE_NIGHT_YES
    }

    // 获取用户保存的头像
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

    // 保存是否显示退出提示
    fun saveExitTipsStatus(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, EXIT_TIPS_STATUS, status)
    }

    // 读取：退出后是否杀进程
    fun getKillProcess(context: Context) = FileUtil.readDataFromSp(context, KILL_PROCESS_STATUS, false)

    // 保存：退出后是否杀进程
    fun saveKillProcess(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, KILL_PROCESS_STATUS, status)
    }

    // 读取：用什么浏览器打开网页
    fun getBrowserType(context: Context) = FileUtil.readDataFromSp(context, NAME_BROWSER_TYPE, 0)

    // 保存：用什么浏览器打开网页
    fun saveBrowserType(context: Context, type: Int) {
        FileUtil.saveDataToSp(context, NAME_BROWSER_TYPE, type)
    }

    // 读取：是否自动检查更新
    fun getAutoCheckUpdate(context: Context) = FileUtil.readDataFromSp(context, NAME_AUTO_UPDATE, false)

    // 保存：是否自动检查更新
    fun saveAutoCheckUpdate(context: Context, status: Boolean) {
        FileUtil.saveDataToSp(context, NAME_AUTO_UPDATE, status)
    }

    // 读取：悬浮壁纸的透明度
    fun getWallpaperTransparent(context: Context) = FileUtil.readDataFromSp(context, NAME_WALLPAPER_TRANSPARENT, 70)

    // 保存：悬浮壁纸的透明度
    fun saveWallpaperTransparent(context: Context, transparent: Int) {
        FileUtil.saveDataToSp(context, NAME_WALLPAPER_TRANSPARENT, transparent)
    }

}