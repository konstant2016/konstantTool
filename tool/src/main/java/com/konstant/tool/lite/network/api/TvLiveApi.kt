package com.konstant.tool.lite.network.api

import com.konstant.tool.lite.network.response.TvLiveResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * 获取直播列表
 */
interface TvLiveApi {

    companion object {
        const val HOST = "https://api2.bmob.cn/"
    }

    @GET("1/classes/tv_live_list/")
    @Headers("X-Bmob-REST-API-Key:c71f1c36342f31fac6fa42cfdad1b18e", "X-Bmob-Application-Id:56b9b188494468733b08edec15d90be8")
    fun getTvLiveList(): Observable<TvLiveResponse>

}