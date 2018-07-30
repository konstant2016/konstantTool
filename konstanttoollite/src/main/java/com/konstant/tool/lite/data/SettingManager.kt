package com.konstant.tool.lite.data

import android.content.Context
import com.konstant.tool.lite.R
import com.konstant.tool.lite.util.FileUtil

/**
 * 描述:保存用户设置
 * 创建人:菜籽
 * 创建时间:2018/4/26 下午7:18
 * 备注:
 */


object SettingManager {


    fun getSwipeBackState(context: Context) =
            FileUtil.readDataFromSp(context, NameConstant.NAME_SWIPEBACK_STATE, false)

    fun setSwipeBackState(context: Context, state: Boolean) {
        FileUtil.saveDataToSp(context, NameConstant.NAME_SWIPEBACK_STATE, state)
    }

    fun getTheme(context: Context) =
            FileUtil.readDataFromSp(context, NameConstant.NAME_SELECTED_THEME, R.style.tool_lite_class)

    fun saveTheme(context: Context, theme: Int) {
        FileUtil.saveDataToSp(context, NameConstant.NAME_SELECTED_THEME, theme)
    }
}