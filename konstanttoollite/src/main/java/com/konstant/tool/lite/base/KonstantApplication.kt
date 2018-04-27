package com.konstant.tool.lite.base

import android.app.Application
import android.util.Log
import com.konstant.tool.lite.data.KonstantDataManager
import com.konstant.tool.lite.util.NetworkUtil

/**
 * 描述:整个应用的application
 * 创建人:菜籽
 * 创建时间:2018/4/25 下午3:58
 * 备注:
 */

class KonstantApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("KonstantApplication","onCreate")
        NetworkUtil.init(applicationContext)
        KonstantDataManager.onCreate(applicationContext)
    }

    override fun onTerminate() {
        Log.d("KonstantApplication","onTerminate")
        KonstantDataManager.onDestroy(applicationContext)
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        KonstantDataManager.onDestroy(applicationContext)
        Log.d("KonstantApplication","onTrimMemory")
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        Log.d("KonstantApplication","onLowMemory")
        KonstantDataManager.onDestroy(applicationContext)
        super.onLowMemory()
    }

}