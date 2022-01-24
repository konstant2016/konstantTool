package com.konstant.tool.lite.network.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.io.Serializable

interface StockDetailApi {

//    companion object {
//        const val HOST = "http://hq.sinajs.cn/"   // 新浪接口
//    }
//
//    @GET("/list={list}")
//    fun getTodayStockDetail(@Path("list") list: String): Observable<ResponseBody>

    companion object {
        const val HOST = "http://sqt.gtimg.cn/"   // 腾讯接口
    }


    @GET("/")
    fun getTodayStockDetail(@Query("q") string: String): Observable<ResponseBody>

}