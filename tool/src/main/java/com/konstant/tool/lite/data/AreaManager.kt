package com.konstant.tool.lite.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.data.entity.Area
import com.konstant.tool.lite.data.entity.China

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
        mChina = JSON.parseObject(text, China::class.java)

        val s = context.assets.open("areacode.json").bufferedReader().readText()
        mAreaList.addAll(JSON.parseArray(s, Area::class.java))
    }

    fun getChina() = mChina

    fun getAreaList() = mAreaList

}