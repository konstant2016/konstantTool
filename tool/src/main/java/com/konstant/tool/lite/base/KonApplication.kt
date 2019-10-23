package com.konstant.tool.lite.base

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import com.konstant.tool.lite.data.KonstantDataManager
import com.konstant.tool.lite.network.NetworkUtil
import com.konstant.tool.lite.util.Density
import com.pgyersdk.crash.PgyCrashManager

/**
 * 描述:整个应用的application
 * 创建人:菜籽
 * 创建时间:2018/4/25 下午3:58
 * 备注:
 */

class KonApplication : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Log.d("KonApplication","onCreate")
        Density.init(this)
        NetworkUtil.init(applicationContext)
        KonstantDataManager.onCreate(applicationContext)
    }

    override fun onTerminate() {
        Log.d("KonApplication","onTerminate")
        KonstantDataManager.onDestroy(applicationContext)
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        KonstantDataManager.onDestroy(applicationContext)
        Log.d("KonApplication","onTrimMemory")
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        Log.d("KonApplication","onLowMemory")
        KonstantDataManager.onDestroy(applicationContext)
        super.onLowMemory()
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = Configuration()
        configuration.setToDefaults()
        resources.updateConfiguration(configuration,resources.displayMetrics)
        return resources
    }

}