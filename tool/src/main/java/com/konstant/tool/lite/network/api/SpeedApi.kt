package com.konstant.tool.lite.network.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

/**
 * 作者：konstant
 * 时间：2019/10/24 11:08
 * 描述：网速测试（下载360卫士实现）
 */

interface SpeedApi {

    companion object {
        const val HOST = "https://dl.360safe.com/"
    }

    @GET("setup.exe")
    @Streaming
    fun getDownload(): Call<ResponseBody>

}