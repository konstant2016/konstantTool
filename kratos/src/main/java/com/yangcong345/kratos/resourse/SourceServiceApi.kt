package com.yangcong345.kratos.resourse

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.Serializable

interface SourceServiceApi {

    /**
     * 获取单个页面的DSL数据和JS脚本
     */
    @GET("")
    fun getPageSource(@Query("pageName") pageName: String): Observable<PageSource>

    /**
     * 获取文件的Stream，用于下载文件
     */
    @Streaming
    @GET
    fun getFileStream(@Url fileUrl: String): Observable<ResponseBody>

}

data class PageSource(
    val pageName: String,
    val code: Code,
    val dsl: Dsl
) : Serializable

data class Code(
    val md5: String,
    val url: String,
    val version: String
) : Serializable

data class Dsl(
    val md5: String,
    val url: String,
    val version: String
) : Serializable