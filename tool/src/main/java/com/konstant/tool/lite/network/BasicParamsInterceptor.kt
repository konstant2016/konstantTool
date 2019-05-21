package com.konstant.tool.lite.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 时间：2019/5/5 16:34
 * 创建：吕卡
 * 描述：网络拦截器
 */

class BasicParamsInterceptor(val context: Context) : Interceptor {

    private val map = HashMap<String, Long>()

    init {
        map[Constant.URL_EXPRESS_GUOGUO] = 20 * 60 * 1000
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        Log.d("网络是否可用:", "" + isNetworkAvailable())

        var request = chain.request()
        if (!isNetworkAvailable()) {
            request = chain.request()
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }

        // 如果网络不可用，那么此链接的缓存时间为 60 * 60 * 24
        if (!isNetworkAvailable()) {
            val maxStale = 60 * 60 * 24
            return chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }

        // 如果网络可用，并且指定了此链接的缓存时间
        val cacheTime = getCacheTime(chain.request().url().host())
        Log.d("缓存链接", chain.request().url().host())
        Log.d("缓存时间", "" + cacheTime)
        if (cacheTime != 0L) {
            return chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=$cacheTime")
                    .build()
        }

        // 网络可用，没有指定缓存时间
        val maxAge = 0
        return chain.proceed(request).newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()

    }

    // 判断网络是否可用
    private fun isNetworkAvailable(): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected && networkInfo.state == NetworkInfo.State.CONNECTED) {
            return true
        }
        return false
    }

    // 获取当前链接的缓存时间，用于对指定链接设定缓存时间
    private fun getCacheTime(hosts: String): Long {
        for (entry in map) {
            if (entry.key.contains(hosts))
                return entry.value
            if (hosts.contains(entry.key))
                return entry.value
        }
        return 0L
    }
}

