package com.konstant.tool.lite.network

import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.module.express.server.ExpressRequest
import com.konstant.tool.lite.module.express.server.ExpressResponse
import com.konstant.tool.lite.module.translate.server.TranslateRequest
import com.konstant.tool.lite.module.translate.server.TranslateResponse
import com.konstant.tool.lite.module.weather.server.AddressResponse
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.konstant.tool.lite.util.MD5

/**
 * 时间：2019/4/24 18:51
 * 创建：吕卡
 * 描述：发起网络请求，并转换为JSON对象
 */

object NetService {

    // 根据城市编码获取天气信息
    fun getWeatherWithCode(code: String, callback: (response: WeatherResponse) -> Unit) {
        val url = Constant.URL_WEATHER + code + ".json"
        NetworkUtil.get(url) { state, data ->
            var response = WeatherResponse()
            if (state) {
                response = JSON.parseObject(String(data), WeatherResponse::class.java)
                response.isSuccess = true
            } else {
                response.isSuccess = false
            }
            callback.invoke(response)
        }
    }

    // 根据经纬度获取地址
    fun getAddressWithLocation(latitude: Double, longitude: Double, callback: (response: AddressResponse) -> Unit) {
        val url = Constant.URL_ADDRESS + "?location=" + longitude + "," + latitude + "&key=" + Constant.KEY_ADDRESS
        NetworkUtil.get(url) { state, data ->
            if (state) {
                callback.invoke(JSON.parseObject(String(data), AddressResponse::class.java))
                return@get
            }
            val response = with(AddressResponse()) { status = "0";this }
            callback.invoke(response)
        }
    }

    // 获取美女图片
    fun getBeautyData(url: String, callback: (Boolean, ByteArray) -> Unit) {
        NetworkUtil.get(url, callback = callback)
    }

    // 获取单张图片
    fun getPicture(url: String, callback: (state: Boolean, data: ByteArray) -> Unit) {
        NetworkUtil.get(url) { state, data -> callback.invoke(state, data) }
    }

    // 物流查询
    fun expressQuery(commanyId: String, num: String, callback: (response: ExpressResponse) -> Unit) {
        val url = Constant.URL_EXPRESS + "?type=" + commanyId + "&postid=" + num
        NetworkUtil.get(url) { state, data ->
            var response = ExpressResponse()
            if (state) {
                response = JSON.parseObject(String(data), ExpressResponse::class.java)
                callback.invoke(response)
                return@get
            }
            response.state = -1
            callback.invoke(response)
        }
    }

    // 翻译
    fun translate(url: String, originString: String, originType: String, resultType: String, appid: String,
                  secret: String, callback: (response: TranslateResponse) -> Unit) {
        val md5 = MD5.md5(appid + originString + System.currentTimeMillis() / 1000 + secret)
        val request = TranslateRequest(originString, originType,
                resultType, appid, (System.currentTimeMillis() / 1000).toInt(), md5)
        val param = JSON.toJSONString(request)
        NetworkUtil.get(url, param) { state, data ->
            var response = TranslateResponse()
            if (state) {
                response = JSON.parseObject(String(data), TranslateResponse::class.java)
            }
            callback.invoke(response)
        }
    }

}