package com.konstant.tool.lite.network.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

interface StockDetailApi {

    companion object {
        const val HOST = "http://ig507.com/data/time/real/"
    }

    @GET("http://ig507.com/data/time/real/{number}?licence=92F15CE7-B220-8998-0802-A08E1233136F")
    fun getTodayStockDetail(@Path("number") number: String): Observable<StockDetailResponse>

}

data class StockDetailResponse(
        val p: Double,   // 当前价格
        val pc: Double,  // 涨跌百分比
        val ud: Double,  // 涨跌价格
        val yc: Double   // 昨日收盘价格
) : Serializable