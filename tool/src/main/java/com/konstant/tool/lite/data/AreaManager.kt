package com.konstant.tool.lite.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.data.weather.China
import com.konstant.tool.lite.data.weather.Area

/**
 * 时间：2018/8/2 17:37
 * 作者：吕卡
 * 描述：
 */
object AreaManager {

    private lateinit var mChina: China
    private val mAreaList = ArrayList<Area>()

    fun onCreate(context: Context) {
        val text = context.assets.open("directdata.json").bufferedReader().readText()
        mChina = Gson().fromJson(text, China::class.java)

        val s = context.assets.open("areacode.json").bufferedReader().readText()
        mAreaList.addAll(Gson().fromJson<List<Area>>(s, object :TypeToken<List<Area>>(){}.type))
    }

    fun getChina() = mChina

    fun getAreaList() = mAreaList

}