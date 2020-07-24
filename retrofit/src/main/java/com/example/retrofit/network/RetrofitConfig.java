package com.example.retrofit.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitConfig {

    private static RetrofitConfig mConfig;
    private static Retrofit.Builder mBuilder;

    public static RetrofitConfig getInstance() {
        if (mConfig == null) {
            synchronized (RetrofitConfig.class) {
                if (mConfig == null) {
                    mConfig = new RetrofitConfig();
                    initBuilder();
                }
            }
        }
        return mConfig;
    }

    private static void initBuilder() {
        mBuilder = new Retrofit.Builder();
        mBuilder.addConverterFactory(ScalarsConverterFactory.create())  // 结果转换为string
                .addConverterFactory(GsonConverterFactory.create())     // 结果转换为JSON对象
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());  // 结果转换为Observable
    }

    public <T> T getApi(String baseUrl, Class<T> clazz) {
        return mBuilder.baseUrl(baseUrl).build().create(clazz);
    }

}
