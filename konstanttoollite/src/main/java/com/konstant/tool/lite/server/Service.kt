package com.konstant.tool.lite.server

import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.server.request.ExpressRequest
import com.konstant.tool.lite.server.request.TranslateRequest
import com.konstant.tool.lite.server.request.WeatherRequest
import com.konstant.tool.lite.util.MD5
import com.konstant.tool.lite.server.net.NetworkUtil
import com.konstant.tool.lite.server.net.UrlConstant

/**
 * 描述:统一的访问服务器工具
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午8:01
 * 备注:
 */

object Service {

    // 翻译
    fun translate(url: String, originString: String, originType: String, resultType: String, appid: String,
                  secret: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        val md5 = MD5.md5("$appid" + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

    // 物流查询
    fun expressQuery(commanyId: String, num: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        val url = UrlConstant.EXPRESS_URL
        val request = ExpressRequest(commanyId, num).toString()
        NetworkUtil.get(url, request, callback)
    }

    // 查询指定地址的天气
    fun locationToCID(url: String, location: String, key: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        val request = WeatherRequest(location, key)
        val param = request.toString()
        NetworkUtil.get(url, param, callback)
    }

    // 查询天气
    fun queryWeather(directNo: String, callback: (state: Boolean, array: ByteArray) -> Unit) {
        "http://tqapi.mobile.360.cn/v4/101010600.json"
        val url = "${UrlConstant.WEATHER_URL}$directNo.json"
        NetworkUtil.get(url, "", callback)
    }

}