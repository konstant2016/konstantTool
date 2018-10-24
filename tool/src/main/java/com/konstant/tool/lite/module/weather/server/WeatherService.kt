package com.konstant.tool.lite.module.weather.server

import com.konstant.tool.lite.network.NetworkUtil
import com.konstant.tool.lite.network.UrlConstant

/**
 * 时间：2018/8/2 16:59
 * 作者：吕卡
 * 描述：
 */
object WeatherService {

    // 查询天气
    fun queryWeather(directNo: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        //"http://tqapi.mobile.360.cn/v4/101010600.json"
        val url = "${UrlConstant.WEATHER_URL}$directNo.json"
        NetworkUtil.get(url, "", callback)
    }
}