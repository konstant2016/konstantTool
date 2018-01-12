package com.konstant.konstanttools.server

import com.alibaba.fastjson.JSON
import com.konstant.konstanttools.util.MD5
import com.konstant.konstanttools.util.NetworkUtil

/**
 * 描述:网络访问
 * 创建人:菜籽
 * 创建时间:2017/12/29 上午11:40
 * 备注:
 */
object Service {

    // 读取归属地
    fun queryPhoneLocation(url: String, phone: String, key: String, callback: (state: Boolean, data: String) -> Unit) {
        val request = PhoneLocationRequest(phone, key)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

    // 查询身份证信息
    fun queryIDNumber(url: String, cardno: String, key: String, callback: (state: Boolean, data: String) -> Unit) {
        val request = IDNumber(cardno, key)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

    // 查询指定地址的天气
    fun queryWeather(url: String, location: String, key: String, callback: (state: Boolean, data: String) -> Unit) {
        val request = WeatherRequest(location, key)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

    // 翻译
    fun translate(url: String, originString: String, originType: String, resultType: String, appid: Int,
                  secret: String, callback: (state: Boolean, data: String) -> Unit) {
        val md5 = MD5.md5("$appid" + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param, callback)
    }

}