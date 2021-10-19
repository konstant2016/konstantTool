package com.konstant.tool.lite.base

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.util.Density
import com.konstant.tool.lite.util.FileUtil
import com.tencent.bugly.crashreport.CrashReport
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
        val show = FileUtil.readDataFromSp(this, SplashActivity.SHOW_DIALOG_KEY, true)
        if (!show){
            CrashReport.initCrashReport(applicationContext,"b3cb53863c",true)
        }
        SettingManager.setSystemChinese(Locale.getDefault().toString().contains("zh"))
        Density.init(this)
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
        // 系统为中文，但是软件显示英文
        if (newConfig.locale.toString().contains("zh")) {
            if (!SettingManager.getSystemChinese()) {
                SettingManager.setSystemChinese(true)
                EventBus.getDefault().post(LanguageChanged())
            }
        // 系统为英文，但是软件显示中文
        } else {
            if (SettingManager.getSystemChinese()) {
                SettingManager.setSystemChinese(false)
                EventBus.getDefault().post(LanguageChanged())
            }
        }
    }
}