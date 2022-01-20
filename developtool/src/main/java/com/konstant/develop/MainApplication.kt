package com.konstant.develop

import android.app.Application
import android.util.Log
import com.tencent.smtt.sdk.QbSdk

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val preinstallStaticTbs = QbSdk.preinstallStaticTbs(this.applicationContext)
        Log.d("MainApplication", "preinstallStaticTbs:$preinstallStaticTbs")
    }
}