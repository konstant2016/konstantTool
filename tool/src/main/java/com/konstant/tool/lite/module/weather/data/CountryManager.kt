package com.konstant.tool.lite.module.weather.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.data.AreaManager
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

/**
 * 描述:用于管理本地保存的城市列表
 * 创建人:菜籽
 * 创建时间:2018/4/7 上午12:32
 * 备注:
 */

object CountryManager {

    private val NAME_LOCAL_CITY = "localCity"
    private val NAME_LOCAL_CITY_ID = "local_city_id"

    // 本地保存的城市列表
    private val mLocalCityList = ArrayList<LocalCountry>()

    // 当前位置的cityCode
    private var mCityCode = ""

    fun onCreate(context: Context) {
        mCityCode = FileUtil.readDataFromSp(context, NAME_LOCAL_CITY_ID)
        val s = FileUtil.readDataFromSp(context, NAME_LOCAL_CITY)
        val array = JSON.parseArray(s, LocalCountry::class.java)
        if (array != null && array.isNotEmpty()) {
            mLocalCityList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            val s1 = JSON.toJSONString(mLocalCityList)
            FileUtil.saveDataToSp(context, NAME_LOCAL_CITY, s1)
            FileUtil.saveDataToSp(context, NAME_LOCAL_CITY_ID, mCityCode)
        }
    }


    // 添加城市
    fun addCity(country: LocalCountry) {
        if (!mLocalCityList.contains(country)) {
            mLocalCityList.add(country)
        }
    }

    // 删除城市
    fun deleteCity(country: LocalCountry) {
        mLocalCityList.remove(country)
    }

    // 读取城市列表
    fun readLocalCityList(): ArrayList<LocalCountry> = mLocalCityList

    // 保存cityCode
    fun setCityCode(cityCode: String) {
        mCityCode = cityCode
    }

    // 读取cityCode
    fun getCityCode() = mCityCode

    // 查找城市对应的天气代号
    fun queryWeatherCode(province: String, city: String, direct: String): String {
        AreaManager.getChina().provinceList.map { prov ->
            if (province.contains(prov.name)) {
                prov.cityList.map { cit ->
                    if (city.contains(cit.name)) {
                        cit.countyList.map { dir ->
                            if (direct.contains(dir.name))
                                return dir.weatherCode
                        }
                    }
                }
            }
        }
        return ""
    }
}
