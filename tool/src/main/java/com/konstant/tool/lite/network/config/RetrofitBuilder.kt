package com.konstant.tool.lite.network.config

import com.google.gson.GsonBuilder
import com.konstant.tool.lite.base.KonApplication
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val mBuilder = Retrofit.Builder()

    init {
        val context = KonApplication.context
        val interceptor = NetworkInterceptor(context)
        val okHttpClient = OkHttpClient.Builder()
                .cache(Cache(context.externalCacheDir!!, 10 * 1024 * 1024))
                .addInterceptor(interceptor)
                .addNetworkInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build()

        val gson = GsonBuilder().setLenient().create()
        mBuilder.addConverterFactory(ScalarsConverterFactory.create())  // 结果转换为string
                .addConverterFactory(GsonConverterFactory.create(gson))     // 结果转换为JSON对象
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // 结果转换为Observable
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .client(okHttpClient)

    }

    fun <T> getApi(baseUrl: String, clazz: Class<T>): T = mBuilder.baseUrl(baseUrl).build().create(clazz)

}