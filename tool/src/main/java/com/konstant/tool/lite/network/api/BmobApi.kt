package com.konstant.tool.lite.network.api

import com.konstant.tool.lite.module.user.UserInfo
import com.konstant.tool.lite.network.response.TvLiveResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

/**
 *  Bmob后台交互的数据
 */
interface BmobApi {

    companion object {
        const val HOST = "https://api2.bmob.cn/"
    }

    /**
     * 获取直播列表
     */
    @GET("1/classes/tv_live_list/")
    @Headers("X-Bmob-REST-API-Key:c71f1c36342f31fac6fa42cfdad1b18e", "X-Bmob-Application-Id:56b9b188494468733b08edec15d90be8")
    fun getTvLiveList(): Observable<TvLiveResponse>


    /**
     * 上传用户信息
     */
    @PUT("1/classes/UserInfo/DVbQ1114")
    @Headers("X-Bmob-REST-API-Key:c71f1c36342f31fac6fa42cfdad1b18e", "X-Bmob-Application-Id:56b9b188494468733b08edec15d90be8")
    fun uploadUserInfo(@Body userInfo: UserInfo): Observable<Response<Any>>

    /**
     * 下载用户信息并保存到本地
     */
    @GET("1/classes/UserInfo/DVbQ1114")
    @Headers("X-Bmob-REST-API-Key:c71f1c36342f31fac6fa42cfdad1b18e", "X-Bmob-Application-Id:56b9b188494468733b08edec15d90be8")
    fun getUserInfo(): Observable<UserInfo>

}