package com.konstant.tool.lite.network.api

import com.konstant.tool.lite.network.response.UpdateResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import io.reactivex.Observable

/**
 * 作者：konstant
 * 时间：2019/10/24 11:01
 * 描述：检查更新接口
 */

interface UpdateApi {

    companion object {
        const val HOST = "https://api2.bmob.cn/"
    }

    @GET("1/classes/update_version/Ddwe3339/")
    @Headers("X-Bmob-REST-API-Key:c71f1c36342f31fac6fa42cfdad1b18e", "X-Bmob-Application-Id:56b9b188494468733b08edec15d90be8")
    fun getUpdate(): Observable<UpdateResponse>

}