package com.konstant.tool.lite.network.api

import com.konstant.tool.lite.network.response.TranslateResponse
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

/**
 * 作者：konstant
 * 时间：2019/10/24 10:54
 * 描述：百度翻译
 */

interface TranslateApi {

    companion object {
        const val HOST = "http://api.fanyi.baidu.com/"
    }

    @GET("api/trans/vip/translate/")
    fun getTranslate(@Query("q") originMsg: String,
                     @Query("from") originType: String,
                     @Query("to") resultType: String,
                     @Query("appid") appid: String,
                     @Query("salt") salt: String,
                     @Query("sign") sign: String): Observable<TranslateResponse>

}