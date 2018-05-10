package com.konstant.tool.lite.server.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BasicParamsInterceptor(val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        Log.d("网络是否可用:", "" + isNetworkAvailable())

        var request = chain.request()
        if(!isNetworkAvailable()){
            request = chain.request()
                    .newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }

        var response:Response
        if (isNetworkAvailable()) {
            val maxAge = 0
            response =  chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
        } else {
            val maxStale = 60 * 60 * 24
            response =  chain.proceed(request).newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
        }

        Log.d("缓存时间",response.headers().toString())
        return response
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
}

