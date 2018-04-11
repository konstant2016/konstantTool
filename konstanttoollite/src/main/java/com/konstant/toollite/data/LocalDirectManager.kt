package com.konstant.toollite.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.toollite.util.NameConstant
import com.konstant.toollite.util.FileUtils

/**
 * 描述:用于管理本地保存的城市列表
 * 创建人:菜籽
 * 创建时间:2018/4/7 上午12:32
 * 备注:
 */

object LocalDirectManager {

    // 添加城市
    fun addCity(context: Context, cityCode: String, cityName: String) {
        val list = ArrayList<LocalDirectData>()
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY)
        if (s != null) {
            val array = JSON.parseArray(s, LocalDirectData::class.java)
            list.addAll(array)
        }
        list.forEach {
            if (it.cityCode == cityCode) return
        }
        list.add(LocalDirectData(cityCode, cityName))
        val s1 = JSON.toJSONString(list)
        FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY, s1)
    }

    // 删除城市
    fun deleteCity(context: Context, direct: LocalDirectData) {
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY) ?: return

        val array = JSON.parseArray(s, LocalDirectData::class.java)
        var dir: LocalDirectData? = null
        array.forEach {
            if (it.cityCode == direct.cityCode) {
                dir = it
            }
        }
        if (dir != null) {
            array.remove(dir)
        }
        val s1 = JSON.toJSONString(array)
        FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY, s1)
    }

    // 读取城市列表
    fun readCityList(context: Context): ArrayList<LocalDirectData> {
        val list = ArrayList<LocalDirectData>()
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_CITY)
        if (s != null) {
            val array = JSON.parseArray(s, LocalDirectData::class.java)
            list.addAll(array)
        }
        return list
    }
}
