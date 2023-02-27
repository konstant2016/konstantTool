package com.konstant.tinkerfix

import android.app.Application
import android.content.Context
import android.content.Intent
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.entry.DefaultApplicationLike
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.shareutil.ShareConstants

/**
 * 时间：2023/1/13 14:45
 * 作者：吕卡
 * 备注：App的Application入口，不再集成系统的Application，而是改为继承自DefaultApplicationLike
 */

@DefaultLifeCycle(
    application = "com.konstant.tinkerfix.MainApplication",
    flags = ShareConstants.TINKER_ENABLE_ALL,
    loadVerifyFlag = false
)

open class TinkerApplication(
    application: Application?,
    tinkerFlags: Int,
    tinkerLoadVerifyFlag: Boolean,
    applicationStartElapsedTime: Long,
    applicationStartMillisTime: Long,
    tinkerResultIntent: Intent?
) : DefaultApplicationLike(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        TinkerInstaller.install(this)
    }

    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callback)
    }

}