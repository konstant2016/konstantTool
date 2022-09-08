package com.konstant.develop.cache

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

object NetworkFileHelper {

    fun getFileSizeWithUrl(url: String): String {
        return try {
            val request = Request.Builder().url(url).head().build()
            val response = OkHttpClient().newCall(request).execute()
            val size = response.header("content-length")?:""
            response.headers().toMultimap().forEach {
                Log.d("NetworkFileHelper","${it.key}----${it.value}")
            }
            response.close()
            Log.d("NetworkFileHelper", "文件大小：${size}")
            size
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("NetworkFileHelper", "文件大小：000")
            ""
        }
    }

}