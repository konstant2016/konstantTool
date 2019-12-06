package com.konstant.tool.httpsender.sender;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientBuilder {

    private static OkHttpClient mClient;

    public static OkHttpClient getClient() {
        if (mClient == null) {
            synchronized (OkHttpClientBuilder.class) {
                if (mClient == null) {
                    mClient = buildClient();
                }
            }
        }
        return mClient;
    }

    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
    }

}
