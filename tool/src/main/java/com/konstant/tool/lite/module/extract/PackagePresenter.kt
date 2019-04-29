package com.konstant.tool.lite.module.extract

import android.content.Context
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.ApplicationUtil
import kotlin.concurrent.thread

object PackagePresenter {

    fun getAllApp(context: Context, callback: (List<AppData>) -> Unit) {
        thread {
            val list = mutableListOf<AppData>()
            ApplicationUtil.getPackageInfoList().forEach {
                val icon = ApplicationUtil.getAppIcon(it)
                val packageName = it.packageName
                val appName = ApplicationUtil.getAppName(it)
                list.add(AppData(packageName, icon, appName))
            }
            callback.invoke(list)
        }
    }

    fun getUserApp(context: Context, callback: (List<AppData>) -> Unit) {
        thread {
            val list = mutableListOf<AppData>()
            ApplicationUtil.getUserAppList().forEach {
                val icon = it.loadIcon(context.packageManager)
                val packageName = it.activityInfo.packageName
                val appName = it.loadLabel(context.packageManager).toString()
                list.add(AppData(packageName, icon, appName))
            }
            callback.invoke(list)
        }
    }

    fun backApp(path: String, appData: AppData, callback: (Boolean) -> Unit) {
        val packageInfo = ApplicationUtil.getPackageInfo(appData.packageName)
        if (packageInfo == null) callback.invoke(false)
        ApplicationUtil.backUserApp(path, packageInfo!!, callback::invoke)
    }

    fun startApp(context: BaseActivity, appData: AppData): Boolean {
        val packageName = appData.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        return context.startActivitySafely(intent)
    }

}