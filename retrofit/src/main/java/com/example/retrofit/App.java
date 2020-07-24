package com.example.retrofit;

import android.app.Application;

public class App extends Application {

    public static boolean isPrd = true;

    @Override
    public void onCreate() {
        super.onCreate();
        // 读取本地配置文件，用来切换 isPrd 的值
    }
}
