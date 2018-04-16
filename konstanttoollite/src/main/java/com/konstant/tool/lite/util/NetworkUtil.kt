package com.konstant.tool.lite.util

import android.content.Context
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

class NetworkUtil {

    companion object {

        private var mNetworkUtil: NetworkUtil? = null
        private var mOkHttpClient: OkHttpClient? = null
        private var mCache: Cache? = null
        private val mCacheSize: Long = 10 * 1024 * 1024    // 缓存大小
        private val mCacheTime = 60 * 60 * 1000                    // 缓存时间
        private lateinit var mInterceptor: BasicParamsInterceptor
        private lateinit var mCacheControl: CacheControl
        private lateinit var mContext: Context

        fun getInstance(context: Context): NetworkUtil {
            @Synchronized
            if (mNetworkUtil == null) {
                // 保存context
                mContext = context

                // 构建缓存
                mCache = Cache(context.externalCacheDir, mCacheSize)

                // 缓存控制
                mCacheControl = CacheControl.Builder().maxAge(mCacheTime, TimeUnit.SECONDS).build()

                // 拦截器
                mInterceptor = BasicParamsInterceptor(context)

                // 构建请求对象
                mOkHttpClient = OkHttpClient.Builder()
                        .cache(mCache)
                        .addInterceptor(mInterceptor)
                        .addNetworkInterceptor(mInterceptor)
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .build()
                // 构建本体
                mNetworkUtil = NetworkUtil()
            }
            return mNetworkUtil!!
        }
    }


    // 发起get请求
    fun get(url: String, param: String, callback: (state: Boolean, data: String) -> Unit) {
        val s = buildGetMethodUrl(param)
        Log.i("get请求参数", url + s)
        val request = Request.Builder()
                .url(url + s)
                .get()
                .build()
        mOkHttpClient?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("response", e.toString())
                callback(false, e.toString())
            }

            override fun onResponse(call: Call?, response: Response) {
                Log.d("response:net", response.networkResponse().toString())
                Log.d("response:cache", response.cacheResponse().toString())
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
