package com.konstant.develop

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.tencent.smtt.sdk.QbSdk
import com.yangcong345.context_provider.ContextProvider

class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ContextProvider.setApplication(this)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        val preinstallStaticTbs = QbSdk.preinstallStaticTbs(this.applicationContext)
        Log.d("MainApplication", "preinstallStaticTbs:$preinstallStaticTbs")
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }
}