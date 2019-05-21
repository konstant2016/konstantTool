package com.konstant.tool.lite.network

import android.content.Context
import android.util.Log
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.base.KonstantApplication
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.TimeUnit

/**
 * 描述:网络访问的工具类
 * 创建人:菜籽
 * 创建时间:2017/12/29 上午11:44
 * 备注:
 */

object NetworkUtil {

    private lateinit var mOkHttpClient: OkHttpClient
    private val mCacheSize: Long = 10 * 1024 * 1024            // 缓存大小
    private val mCacheTime = 60 * 60                           // 缓存时间

    fun init(context: Context) {
        // 构建缓存
        val mCache = Cache(context.externalCacheDir, mCacheSize)

        // 缓存控制
        val mCacheControl = CacheControl.Builder().maxAge(mCacheTime, TimeUnit.SECONDS).build()

        // 拦截器
        val mInterceptor = BasicParamsInterceptor(context)

        // 构建请求对象
        mOkHttpClient = OkHttpClient.Builder()
                .cache(mCache)
                .addInterceptor(mInterceptor)
                .addNetworkInterceptor(mInterceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build()
    }

    // 发起get请求
    fun get(url: String, param: String = "", callback: (state: Boolean, data: ByteArray) -> Unit) {
        val s = buildGetMethodUrl(param)
        Log.i("get请求参数", url + s)
        val request = Request.Builder()
                .url(url + s)
                .get()
                .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("response", e.toString())
                callback(false, e.toString().toByteArray())
            }

            override fun onResponse(call: Call?, response: Response) {
                Log.d("response:net", response.networkResponse().toString())
                Log.d("response:cache", response.cacheResponse().toString())
                if (!response.isSuccessful) {
                    callback(false, response.body()?.bytes() ?: ByteArray(0))
                } else {
                    callback(true, response.body()?.bytes() ?: ByteArray(0))
                }
            }
        })
    }

    // 构建GET请求的路径
    private fun buildGetMethodUrl(params: String): String {
        if (params.isEmpty()) return ""
        val map = JSON.parseObject(params, Map::class.java)
        val buffer = StringBuffer()
        buffer.append("?")
        for ((key, value) in map) {
            buffer.append(key).append("=").append(value).append("&")
        }
        return buffer.toString()
    }


    // 下载大文件
    fun downloadFile(url: String, max: Long = 0L, path: String, progressResult: (current: Long, total: Long, status: Boolean) -> Unit) {
        val request = Request.Builder()
                .url(url)
                .get()
                .build()
        mOkHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                progressResult.invoke(0, 1, false)
            }

            override fun onResponse(call: Call?, response: Response) {
                try {
                    val inputStream = response.body()!!.byteStream()
                    val length = response.body()!!.contentLength()
                    val outputStream = FileOutputStream(File(path))
                    val bytes = ByteArray(1024)
                    var ch = -1
                    var process = 0L
                    while (inputStream.read(bytes).also { ch = it } != -1) {
                        outputStream.write(bytes)
                        process += ch
                        progressResult.invoke(process, length, true)
                        if (max != 0L && process >= max) break
                    }
                    outputStream.flush()
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    progressResult.invoke(0, 1, false)
                }
            }
        })
    }
}
