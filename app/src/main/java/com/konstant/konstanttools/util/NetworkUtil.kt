package com.konstant.konstanttools.util

import android.util.Log
import com.alibaba.fastjson.JSON
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 描述:网络访问的工具类
 * 创建人:菜籽
 * 创建时间:2017/12/29 上午11:44
 * 备注:
 */

object NetworkUtil {

    val GET = 0;
    val POST = 1;
    val PUT = 2

    private val mOkHttpClient: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

    fun get(url: String, param: String, callback: (state: Boolean, data: String) -> Unit) {
        val s = buildGetMethodUrl(param)
        Log.i("get请求参数", url + s)
        val request = Request.Builder()
                .url(url + s)
                .get()
                .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                callback(false, e.toString())
            }

            override fun onResponse(call: Call?, response: Response) {
                if (!response.isSuccessful) {
                    callback(false, response.toString())
                } else {
                    callback(true, response.body()!!.string())
                }
            }
        })

    }

    // 构建GET请求的路径
    private fun buildGetMethodUrl(params: String): String {
        if (params.isNullOrEmpty()) return ""
        val map = JSON.parseObject(params, Map::class.java)
        val buffer = StringBuffer()
        buffer.append("?")
        for ((key, value) in map) {
            buffer.append(key).append("=").append(value).append("&")
        }
        return buffer.toString()
    }

}
