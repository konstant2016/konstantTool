package com.konstant.tool.lite.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.util.FileUtils
import java.util.concurrent.Executors

/**
 * 描述:用于管理本地保存的城市列表
 * 创建人:菜籽
 * 创建时间:2018/4/7 上午12:32
 * 备注:
 */

object LocalCountryManager {

    // 本地保存的城市列表
    private val mCityList = ArrayList<LocalCountry>()

    // 当前位置的cityCode
    private var mCityCode = ""

    fun onCreate(context: Context) {
        val code = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY_ID)
        mCityCode = code ?: ""
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY)
        if (s != null) {
            val array = JSON.parseArray(s, LocalCountry::class.java)
            mCityList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            val s1 = JSON.toJSONString(mCityList)
            FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY, s1)
            FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY_ID, mCityCode)
        }
    }

    // 添加城市
    fun addCity(country: LocalCountry) {
        if (!mCityList.contains(country)) {
            mCityList.add(country)
        }
    }

    // 删除城市
    fun deleteCity(country: LocalCountry) {
        mCityList.remove(country)
    }

    // 读取城市列表
    fun readCityList(): ArrayList<LocalCountry> = mCityList

    // 保存cityCode
    fun setCityCode(cityCode: String) {
        mCityCode = cityCode
    }

    // 读取cityCode
    fun getCityCode() = mCityCode


}