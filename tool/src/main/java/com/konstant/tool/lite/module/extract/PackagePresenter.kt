package com.konstant.tool.lite.module.extract

import android.content.Context
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.AppUtil
import kotlin.concurrent.thread

object PackagePresenter {

    fun getAppList(withSystem: Boolean, context: Context, callback: (List<AppData>) -> Unit) {
        if (withSystem){
            getAllApp(callback)
        }else{
            getUserApp(context,callback)
        }
    }

    private fun getAllApp(callback: (List<AppData>) -> Unit) {
        thread {
            val list = mutableListOf<AppData>()
            AppUtil.getPackageInfoList().forEach {
                val icon = AppUtil.getAppIcon(it)
                val packageName = it.packageName
                val appName = AppUtil.getAppName(it)
                list.add(AppData(packageName, icon, appName))
                list.sort()
            }
            callback.invoke(list)
        }
    }

    private fun getUserApp(context: Context, callback: (List<AppData>) -> Unit) {
        thread {
            val list = mutableListOf<AppData>()
            AppUtil.getUserAppList().forEach {
                val icon = it.loadIcon(context.packageManager)
                val packageName = it.activityInfo.packageName
                val appName = it.loadLabel(context.packageManager).toString()
                list.add(AppData(packageName, icon, appName))
                list.sort()
            }
            callback.invoke(list)
        }
    }

    fun backApp(path: String, appData: AppData, callback: (Boolean) -> Unit) {
        val packageInfo = AppUtil.getPackageInfo(appData.packageName)
        if (packageInfo == null) callback.invoke(false)
        AppUtil.backUserApp(path, packageInfo!!, callback::invoke)
    }

    fun startApp(context: BaseActivity, appData: AppData): Boolean {
        val packageName = appData.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        return context.startActivitySafely(intent)
    }

}