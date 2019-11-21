package com.konstant.tool.lite.module.weather.fragment

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.data.WeatherData
import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.network.response.WeatherResponse
import io.reactivex.disposables.CompositeDisposable
import kotlin.concurrent.thread

/**
 * 时间：2019/4/24 18:18
 * 创建：吕卡
 * 描述：查询天气信息，获取定位信息，经纬度转位置，位置转城市编码等
 */

class WeatherPresenter(private val context: Context, private val mDisposable: CompositeDisposable) {

    private val TAG = "WeatherPresenter"
    private val locationClient by lazy { AMapLocationClient(context) }

    interface WeatherResult {
        fun onSuccess(weatherList: List<Any>, cityName: String, directCode: String)
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
            Log.d(TAG, "省：" + location.province + "，市：" + location.city + "，区：" + location.district)
            getCodeWithAddress(location.province, location.city, location.district) { weatherCode ->
                getWeatherWithCode(weatherCode, result)
            }
        }
    }

    // 利用高德定位获取城市信息
    private fun getCurrentAddress(callback: (location: AMapLocation) -> Unit) {
        Log.d(TAG, "开始利用高德定位")
        with(locationClient) {
            setLocationOption(with(AMapLocationClientOption()) {
                locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                this
            })
            setLocationListener {
                if (it.errorCode == AMapLocation.LOCATION_SUCCESS
                        && !TextUtils.isEmpty(it.province)
                        && !TextUtils.isEmpty(it.city)
                        && !TextUtils.isEmpty(it.district)) {
                    locationClient.stopLocation()
                    callback.invoke(it)
                }
            }
            startLocation()
        }
    }

    // 根据位置信息获取城市编码
    private fun getCodeWithAddress(province: String, city: String, direct: String, result: (code: String) -> Unit) {
        thread {
            val code = CountryManager.queryWeatherCode(province, city, direct)
            Log.d(TAG, "城市编码：$code")
            result.invoke(code)
        }
    }

    // 根据位置编码获取天气信息
    fun getWeatherWithCode(code: String, result: WeatherResult) {
        val disposable = NetworkHelper.getWeatherWithCode(code)
                .subscribe({
                    val province = it.area[0][0]
                    val direct = it.area[2][0]
                    val cityName = if (province == direct) province else "${it.area[0][0]} ${it.area[2][0]}"
                    result.onSuccess(buildWeatherData(it), cityName, code)
                }, {
                    it.printStackTrace()
                    result.onWeatherError()
                })
        mDisposable.add(disposable)
    }

    // 请求到的数据进行转换
    private fun buildWeatherData(weather: WeatherResponse): List<Any> {
        val weatherList = mutableListOf<Any>()
        val alert = if (weather.alert != null && weather.alert.size > 0) {
            weather.alert[0].content
        } else {
            ""
        }
        val alertUrl = if (weather.alert != null && weather.alert.size > 0) {
            weather.alert[0].originUrl
        } else {
            ""
        }
        val current = WeatherData().Current(alert,
                alertUrl,
                weather.realtime.dataUptime,
                weather.realtime.weather.temperature,
                weather.realtime.wind.direct,
                weather.realtime.wind.power,
                weather.realtime.weather.info)
        // 添加当前天气
        weatherList.add(current)

        // 添加逐小时预报标题
        weatherList.add(WeatherData().Title("逐小时预报:"))
        val hourList = ArrayList<WeatherData.Hour.HourData>()
        weather.hourly_forecast.forEach {
            val hourData = WeatherData().Hour().HourData(it.img, it.hour, it.temperature)
            hourList.add(hourData)
        }
        val hour = WeatherData().Hour(hourList)
        // 添加逐小时预报数据
        weatherList.add(hour)

        // 添加未来15天天气标题
        weatherList.add(WeatherData().Title("最近15天天气预报:"))
        weather.weather.forEach {
            val dayWeather = WeatherData().Day(it.date,
                    it.info.day[1],
                    it.info.night[1],
                    it.info.day[2].toInt(),
                    it.info.night[2].toInt(),
                    it.info.day[3],
                    it.info.day[4],
                    it.info.day[0].toInt())
            // 添加未来15天天气数据
            weatherList.add(dayWeather)
        }

        // 添加天气指数标题
        weatherList.add(WeatherData().Title("(${weather.life?.date})天气指数："))
        // 添加天气指数数据
        weather.life?.info?.run {
            kongtiao?.let { weatherList.add(WeatherData().Life().LifeData("空调：${it[0]}", it[1])) }
            daisan?.let { weatherList.add(WeatherData().Life().LifeData("带伞：${it[0]}", it[1])) }
            ziwaixian?.let { weatherList.add(WeatherData().Life().LifeData("紫外线：${it[0]}", it[1])) }
            yundong?.let { weatherList.add(WeatherData().Life().LifeData("运动：${it[0]}", it[1])) }
            ganmao?.let { weatherList.add(WeatherData().Life().LifeData("感冒：${it[0]}", it[1])) }
            xiche?.let { weatherList.add(WeatherData().Life().LifeData("洗车：${it[0]}", it[1])) }
            diaoyu?.let { weatherList.add(WeatherData().Life().LifeData("钓鱼：${it[0]}", it[1])) }
            guomin?.let { weatherList.add(WeatherData().Life().LifeData("过敏：${it[0]}", it[1])) }
            xianxing?.let { weatherList.add(WeatherData().Life().LifeData("限行：${it[0]}", it[1])) }
            wuran?.let { weatherList.add(WeatherData().Life().LifeData("污染：${it[0]}", it[1])) }
            chuanyi?.let { weatherList.add(WeatherData().Life().LifeData("穿衣：${it[0]}", it[1])) }
        }

        return weatherList
    }

    // 页面退出时调用，避免内存泄漏
    fun onDestroy() {
        locationClient.onDestroy()
    }

}