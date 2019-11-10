package com.konstant.tool.lite.base

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import com.konstant.tool.lite.data.KonstantDataManager
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.util.Density
import org.greenrobot.eventbus.EventBus
import java.util.*

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
        SettingManager.setSystemChinese(Locale.getDefault().toString().contains("zh"))
        Log.d("KonApplication", "onCreate")
        Density.init(this)
        KonstantDataManager.onCreate(applicationContext)
    }

    override fun onTerminate() {
        Log.d("KonApplication", "onTerminate")
        KonstantDataManager.onDestroy(applicationContext)
        super.onTerminate()
    }

    override fun onTrimMemory(level: Int) {
        KonstantDataManager.onDestroy(applicationContext)
        Log.d("KonApplication", "onTrimMemory")
        super.onTrimMemory(level)
    }

    override fun onLowMemory() {
        Log.d("KonApplication", "onLowMemory")
        KonstantDataManager.onDestroy(applicationContext)
        super.onLowMemory()
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        val configuration = Configuration()
        configuration.setToDefaults()
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return resources
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.locale.toString().contains("zh")) {
            SettingManager.setSystemChinese(true)
        } else {
            SettingManager.setSystemChinese(false)
        }
        EventBus.getDefault().post(LanguageChanged())
    }
}