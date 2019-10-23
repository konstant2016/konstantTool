package com.konstant.tool.lite.network

import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.express.server.ExpressResponse
import com.konstant.tool.lite.module.express.server.ExpressResponseGuoGuo
import com.konstant.tool.lite.module.setting.UpdateResponse
import com.konstant.tool.lite.module.translate.server.TranslateRequest
import com.konstant.tool.lite.module.translate.server.TranslateResponse
import com.konstant.tool.lite.module.weather.server.AddressResponse
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.konstant.tool.lite.util.MD5
import java.io.File
import kotlin.Exception

/**
 * 时间：2019/4/24 18:51
 * 创建：吕卡
 * 描述：发起网络请求，并转换为JSON对象
 */

object NetService {

    // 根据城市编码获取天气信息
    fun getWeatherWithCode(code: String, callback: (response: WeatherResponse) -> Unit) {
        val url = Constant.URL_WEATHER + code + ".json"
        NetworkUtil.get(url = url) { state, data ->
            var response = WeatherResponse()
            if (state and data.isNotEmpty()) {
                try {
                    response = JSON.parseObject(String(data), WeatherResponse::class.java)
                    response.isSuccess = true
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    response.isSuccess = false
                }
            } else {
                response.isSuccess = false
            }
            callback.invoke(response)
        }
    }

    // 根据经纬度获取地址
    fun getAddressWithLocation(latitude: Double, longitude: Double, callback: (response: AddressResponse) -> Unit) {
        val url = Constant.URL_ADDRESS + "?location=" + longitude + "," + latitude + "&key=" + Constant.KEY_ADDRESS
        NetworkUtil.get(url = url) { state, data ->
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
        NetworkUtil.get(url = url, callback = callback)
    }

    // 获取单张图片
    fun getPicture(url: String, callback: (state: Boolean, data: ByteArray) -> Unit) {
        NetworkUtil.get(url = url) { state, data -> callback.invoke(state, data) }
    }

    // 快递100的物流查询接口
    fun expressQuery(companyId: String, num: String, callback: (response: ExpressResponse) -> Unit) {
        val url = Constant.URL_EXPRESS + "?type=" + companyId + "&postid=" + num
        NetworkUtil.get(url = url) { state, data ->
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

    // 菜鸟裹裹的物流查询
    fun expressQuery(expressNo: String, callback: (response: ExpressResponseGuoGuo) -> Unit) {
        val url = Constant.URL_EXPRESS_GUOGUO + "?q=" + expressNo
        NetworkUtil.get(url = url) { state, data ->
            var response = ExpressResponseGuoGuo()
            if (state) {
                try {
                    response = JSON.parseObject(String(data), ExpressResponseGuoGuo::class.java)
                } catch (e: Exception) {
                    response.status = -1
                }
            } else {
                response.status = -1
            }
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
        NetworkUtil.get(url = url, param = param) { state, data ->
            var response = TranslateResponse()
            if (state) {
                response = JSON.parseObject(String(data), TranslateResponse::class.java)
            }
            callback.invoke(response)
        }
    }

    // 下载360卫士
    fun downloadSafe(max: Long, callback: (current: Long, total: Long, status: Boolean) -> Unit) {
        val url = "https://dl.360safe.com/setup.exe"
        val path = "${KonApplication.context.externalCacheDir}" + File.separator + "fileStamp"
        NetworkUtil.downloadFile(url, max, path) { current, total, status ->
            callback.invoke(current, total, status)
        }
    }

    // 获取更新信息
    fun getUpdateVersion(callback: (UpdateResponse) -> Unit) {
        val header = HashMap<String, String>()
        header["X-Bmob-Application-Id"] = "56b9b188494468733b08edec15d90be8"
        header["X-Bmob-REST-API-Key"] = "c71f1c36342f31fac6fa42cfdad1b18e"
        val url = "https://api2.bmob.cn/1/classes/update_version/Ddwe3339"
        NetworkUtil.get(header = header, url = url) { state, data ->
            if (!state) {
                callback.invoke(UpdateResponse())
                return@get
            }
            val response = JSON.parseObject(String(data), UpdateResponse::class.java)
            callback.invoke(response)
        }
    }

}