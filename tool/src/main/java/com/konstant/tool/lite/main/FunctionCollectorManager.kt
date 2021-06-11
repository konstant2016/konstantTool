package com.konstant.tool.lite.main

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.bean.main.Function
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

/**
 * 作者：konstant
 * 时间：2019/11/9 18:00
 * 描述：功能收藏管理
 */

object FunctionCollectorManager {

    private const val FUNCTION_COLLECTION = "Function_Collection"
    private val mFunctionList = ArrayList<Function>()

    fun onCreate(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            val config = FileUtil.readDataFromSp(context, FUNCTION_COLLECTION, "")
            if (config.isNotEmpty()) {
                val configs = Gson().fromJson<List<Function>>(config, object : TypeToken<List<Function>>() {}.type)
                mFunctionList.clear()
                mFunctionList.addAll(configs)
            }
        }
    }

    fun onDestroy(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            val json = Gson().toJson(mFunctionList)
            FileUtil.saveDataToSp(context, FUNCTION_COLLECTION, json)
        }
    }

    fun getCollectionFunction() = mFunctionList

    fun addCollectionFunction(data: Function) {
        if (!mFunctionList.contains(data)) {
            mFunctionList.add(data)
        }
    }

    fun removeCollectionFunction(data: Function) {
        mFunctionList.remove(data)
    }

    fun containsFunction(data: Function) = mFunctionList.contains(data)

    fun saveSyncCollection(context: Context, string: String) {
        val configs = Gson().fromJson<List<Function>>(string, object : TypeToken<List<Function>>() {}.type)
        mFunctionList.clear()
        mFunctionList.addAll(configs)
        val json = Gson().toJson(mFunctionList)
        FileUtil.saveDataToSp(context, FUNCTION_COLLECTION, json)
    }

}