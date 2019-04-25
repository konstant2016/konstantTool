package com.konstant.tool.lite.module.weather.fragment

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.konstant.tool.lite.network.NetService

/**
 * 时间：2019/4/24 18:18
 * 创建：吕卡
 * 描述：查询天气信息，获取定位信息，经纬度转位置，位置转城市编码等
 */

class WeatherPresenter(private val context: Context) {

    private val TAG = "WeatherPresenter"
    private val locationClient by lazy { AMapLocationClient(context) }

    interface WeatherResult {
        fun onSuccess(response: WeatherResponse, directCode: String)
        fun onLocationError()
        fun onWeatherError()
    }

    // 获取当前城市的天气信息
    fun getCurrentLocationWeather(result: WeatherResult) {
        getCurrentAddress { location ->
            Log.d(TAG, "得到定位结果:" + location.errorCode)
            if (location.errorCode != AMapLocation.LOCATION_SUCCESS) {
                result.onLocationError()
                return@getCurrentAddress
            }
            val weatherCode = getCodeWithAddress(location.province, location.city, location.district)
            getWeatherWithCode(weatherCode) { weather ->
                if (!weather.isSuccess) {
                    result.onWeatherError()
                    return@getWeatherWithCode
                }
                result.onSuccess(weather, weatherCode)
            }
        }
    }

    // 利用高德定位获取城市信息
    private fun getCurrentAddress(callback: (location: AMapLocation) -> Unit) {
        Log.d(TAG, "开始利用高德定位")
        with(locationClient) {
            setLocationOption(with(AMapLocationClientOption()) {
                locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
                this
            })
            setLocationListener {
                callback.invoke(it)
            }
            startLocation()
        }
    }

    // 根据位置信息获取城市编码
    private fun getCodeWithAddress(province: String, city: String, direct: String): String {
        val code = CountryManager.queryWeatherCode(province, city, direct)
        Log.d(TAG, "城市编码：$code")
        return code
    }


    // 根据位置编码获取天气信息
    fun getWeatherWithCode(code: String, result: (response: WeatherResponse) -> Unit) {
        NetService.getWeatherWithCode(code) {
            Log.d(TAG, "查询天气结果:" + JSON.toJSON(it))
            result.invoke(it)
        }
    }

    // 页面退出时调用，避免内存泄漏
    fun onDestroy() {
        locationClient.onDestroy()
    }

}