package com.konstant.tool.lite.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.server.other.China
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

/**
 * 描述:用于管理本地保存的城市列表
 * 创建人:菜籽
 * 创建时间:2018/4/7 上午12:32
 * 备注:
 */

object CountryManager {

    // 本地保存的城市列表
    private val mLocalCityList = ArrayList<LocalCountry>()
    private val mProvinceList = ArrayList<China.Province>()
    private val mCityList = ArrayList<China.Province.City>()
    private val mCountryList = ArrayList<China.Province.City.County>()
    private val mCountryNameList = ArrayList<String>()

    // 当前位置的cityCode
    private var mCityCode = ""
    private lateinit var mChina: China

    fun onCreate(context: Context) {
        parseChina(context)
        mCityCode = FileUtil.readDataFromSp(context, NameConstant.NAME_LOCAL_CITY_ID)
        val s = FileUtil.readDataFromSp(context, NameConstant.NAME_LOCAL_CITY)
        val array = JSON.parseArray(s, LocalCountry::class.java)
        if (array != null && array.isNotEmpty()) {
            mLocalCityList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        Executors.newSingleThreadExecutor().execute {
            val s1 = JSON.toJSONString(mLocalCityList)
            FileUtil.saveDataToSp(context, NameConstant.NAME_LOCAL_CITY, s1)
            FileUtil.saveDataToSp(context, NameConstant.NAME_LOCAL_CITY_ID, mCityCode)
        }
    }

    private fun parseChina(context: Context) {
        val text = context.assets.open("directdata.json").bufferedReader().readText()
        mChina = JSON.parseObject(text, China::class.java)

        mChina.provinceList.map {province->
            mProvinceList.add(province)
            province.cityList.map {city->
                mCityList.add(city)
                city.countyList.map {country->
                    mCountryList.add(country)
                    mCountryNameList.add("${country.name}-${province.name}")
                }
            }
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

    // 读取省份列表
    fun getProvinceList() = mProvinceList

    // 读取城市列表
    fun getCityList() = mCityList

    // 读取县级列表
    fun getCountryList() = mCountryList

    // 获取县级名称列表
    fun getCountryNameList() = mCountryNameList


}
