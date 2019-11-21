package com.konstant.tool.lite.network.api

import com.konstant.tool.lite.network.response.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
* 作者：konstant
* 时间：2019/10/24 10:48
* 描述：360天气的接口
*/

interface WeatherApi {

    companion object {
        const val HOST = "http://tqapi.mobile.360.cn/"
    }

    @GET("v4/{path}.json")
    fun getWeatherWithCode(@Path("path") cityCode: String): Observable<WeatherResponse>

}