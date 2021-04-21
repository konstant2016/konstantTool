package com.konstant.tool.lite.network.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.io.Serializable

interface StockDetailApi {

    companion object {
        const val HOST = "http://hq.sinajs.cn/"
    }

    @GET("/list={list}")
    fun getTodayStockDetail(@Path("list") list: String): Observable<ResponseBody>

}