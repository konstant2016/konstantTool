package com.konstant.tool.lite.module.busline.data

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.util.FileUtil

/**
 * 时间：2018/8/3 9:37
 * 作者：吕卡
 * 描述：
 */
object QueryHistoryManager {

    private val mWrapperList = ArrayList<QueryWrapper>()
    private val NAME_QUERY_HISTORY = "query_history"

    fun onCreate(context: Context) {
        val bytes = FileUtil.readFileFromFile(context, NAME_QUERY_HISTORY)
        if (bytes.isEmpty()) return
        val list = JSON.parseArray(String(bytes, 0, bytes.size), QueryWrapper::class.java)
        mWrapperList.addAll(list)
    }

    fun onDestroy(context: Context) {
        val s = JSON.toJSONString(mWrapperList)
        FileUtil.saveFileToFile(context, NAME_QUERY_HISTORY, s.toByteArray())
    }

    fun getQueryHistory() = mWrapperList

    fun addQueryHistory(wrapper: QueryWrapper) {
        Log.d("query_history", "" + mWrapperList.contains(wrapper))
        if (!mWrapperList.contains(wrapper)) {
            mWrapperList.add(wrapper)
        }
        Log.d("query_history", "size:" + mWrapperList.size)
    }

    fun removeQueryHistory(wrapper: QueryWrapper) {
        mWrapperList.remove(wrapper)
    }

}