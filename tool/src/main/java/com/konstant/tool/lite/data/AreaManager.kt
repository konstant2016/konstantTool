package com.konstant.tool.lite.data

import android.content.Context
import com.google.gson.Gson
import com.konstant.tool.lite.data.bean.weather.China
import java.util.concurrent.Executors

/**
 * 时间：2018/8/2 17:37
 * 作者：吕卡
 * 描述：
 */
object AreaManager {

    private lateinit var mChina: China

    fun onCreate(context: Context){
        Executors.newSingleThreadExecutor()
                .execute {
                    val text = context.assets.open("DirectdData.json").bufferedReader().readText()
                    mChina = Gson().fromJson(text, China::class.java)
                }
    }

    fun getChina() = mChina

}