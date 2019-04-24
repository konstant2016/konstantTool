package com.konstant.tool.lite.module.weather.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.konstant.tool.lite.base.KonstantApplication
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.konstant.tool.lite.network.NetService

/**
 * 时间：2019/4/24 18:18
 * 创建：吕卡
 * 描述：查询天气信息，获取定位信息，经纬度转位置，位置转城市编码等
 */

class WeatherPresenter {

    // 获取当前城市的天气信息
    fun getCurrentLocationWeather(result: (response: WeatherResponse, directCode: String) -> Unit) {
        getCurrentLocation { latitude, longitude ->
            getAddressWithLocation(latitude, longitude) { province: String, city: String, direct: String ->
                val code = getCodeWithAddress(province, city, direct)
                getWeatherWithCode(code) { response ->
                    result.invoke(response, code)
                }
            }
        }
    }

    // 利用原生定位，获取经纬度信息
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(result: (latitude: Double, longitude: Double) -> Unit) {
        val locationManager = KonstantApplication.sContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30, 5000f, object : LocationListener {
            override fun onLocationChanged(location: Location) {
                result.invoke(location.latitude, location.longitude)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }

            override fun onProviderEnabled(provider: String?) {
            }

            override fun onProviderDisabled(provider: String?) {
            }
        })
    }

    // 根据经纬度获取位置信息
    private fun getAddressWithLocation(latitude: Double, longitude: Double, result: (province: String, city: String, direct: String) -> Unit) {
        NetService.getAddressWithLocation(latitude, longitude) {
            if (it.status == "1") {
                val address = it.regeocode.addressComponent
                result.invoke(address.province, "", address.district)
            }else{
                result.invoke("", "", "")
            }
        }
    }

    // 根据位置信息获取城市编码
    private fun getCodeWithAddress(province: String, city: String, direct: String) = CountryManager.queryWeatherCode(province, city, direct)


    // 根据位置编码获取天气信息
    fun getWeatherWithCode(code: String, result: (response: WeatherResponse) -> Unit) {
        NetService.getWeatherWithCode(code) { result.invoke(it) }
    }

}