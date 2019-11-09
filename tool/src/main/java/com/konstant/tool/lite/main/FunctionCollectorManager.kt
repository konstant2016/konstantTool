package com.konstant.tool.lite.main

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.bean.main.ConfigData
import com.konstant.tool.lite.util.FileUtil

/**
 * 作者：konstant
 * 时间：2019/11/9 18:00
 * 描述：功能收藏管理
 */

object FunctionCollectorManager {

    private const val FUNCTION_COLLECTION = "Function_Collection"
    private val mConfigs = ArrayList<ConfigData>()

    fun onCreate(context: Context) {
        val config = FileUtil.readDataFromSp(context, FUNCTION_COLLECTION, "")
        if (config.isNotEmpty()) {
            val configs = Gson().fromJson<List<ConfigData>>(config, object : TypeToken<List<ConfigData>>() {}.type)
            mConfigs.addAll(configs)
        }
    }

    fun onDestroy(context: Context) {
        val json = Gson().toJson(mConfigs)
        FileUtil.saveDataToSp(context, FUNCTION_COLLECTION, json)
    }

    fun getCollectionFunction() = mConfigs

    fun addCollectionFunction(data: ConfigData) {
        if (!mConfigs.contains(data)) {
            mConfigs.add(data)
        }
    }

    fun removeCollectionFunction(data: ConfigData) {
        mConfigs.remove(data)
    }

    fun containsFunction(data: ConfigData) = mConfigs.contains(data)

}