package com.konstant.tool.lite.network.api;

import com.konstant.tool.lite.network.response.ExpressResponse
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable

/**
 * 作者：konstant
 * 时间：2019/10/24 10:49
 * 描述：菜鸟裹裹的快递查询
 */

interface ExpressApi {

    companion object {
        val HOST = "http://coldsong.cn/"
    }

    @GET("letter/api/v1/kuaidi.php/")
    fun getExpressInfo(@Query("q") expressNo: String): Observable<ExpressResponse>
}
