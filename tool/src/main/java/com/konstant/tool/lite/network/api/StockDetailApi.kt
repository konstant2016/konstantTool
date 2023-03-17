package com.konstant.tool.lite.network.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface StockDetailApi {

//    companion object {
//        const val HOST = "http://hq.sinajs.cn/"   // 新浪接口
//    }
//
//    @GET("/list={list}")
//    fun getTodayStockDetail(@Path("list") list: String): Observable<ResponseBody>

    companion object {
        const val DETAIL_HOST = "http://sqt.gtimg.cn/"   // 腾讯接口
        const val BRIEF_HOST = "http://qt.gtimg.cn/"   // 腾讯接口
    }


    @GET("/")
    fun getTodayStockDetail(@Query("q") string: String): Observable<ResponseBody>


    @GET("/")
    fun getTodayStockBrief(@Query("q") string: String): Observable<ResponseBody>

}