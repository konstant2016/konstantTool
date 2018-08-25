package com.konstant.tool.lite.module.setting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
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

    private val NAME_SELECTED_THEME = "selectedTheme"
    private val NAME_SWIPEBACK_STATE = "swipeBackState"
    val NAME_USER_HEADER = "header_big.jpg"
    val NAME_HEADER_THUMB = "header_small.jpg"

    fun saveTheme(context: Context, theme: Int) {
        FileUtil.saveDataToSp(context, NAME_SELECTED_THEME, theme)
    }

    fun setSwipeBackState(context: Context, state: Boolean) {
        FileUtil.saveDataToSp(context, NAME_SWIPEBACK_STATE, state)
    }

    fun deleteUserHeaderThumb(context: Context) {
        File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), NAME_HEADER_THUMB).delete()
    }

    fun getTheme(context: Context) =
            FileUtil.readDataFromSp(context, NAME_SELECTED_THEME, R.style.tool_lite_class)

    fun getSwipeBackState(context: Context) =
            FileUtil.readDataFromSp(context, NAME_SWIPEBACK_STATE, false)

    fun getUserHeaderThumb(context: Context): Bitmap {
        val path = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}${File.separator}$NAME_HEADER_THUMB"
        return if (File(path).exists()) {
            BitmapFactory.decodeFile(path)
        } else {
            BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher)
        }
    }

}