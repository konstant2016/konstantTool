package com.konstant.tool.lite.module.extract

import android.content.Context
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.AppUtil
import java.io.File
import kotlin.concurrent.thread

object PackagePresenter {

    fun getAppList(withSystem: Boolean, context: Context, callback: (List<AppData>) -> Unit) {
        if (withSystem) {
            getAllApp(callback)
        } else {
            getUserApp(context, callback)
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
            }
            callback.invoke(sortList(list))
        }
    }

    private fun sortList(list: List<AppData>): List<AppData> {
        val englishList = mutableListOf<AppData>()
        val chineseList = mutableListOf<AppData>()
        list.forEach {
            val s = it.appName[0]
            if ((s in 'A'..'Z') or (s in 'a'..'z')) {
                englishList.add(it)
            } else {
                chineseList.add(it)
            }
        }
        englishList.sort()
        chineseList.sort()
        return englishList.plus(chineseList)
    }

    private fun getUserApp(context: Context, callback: (List<AppData>) -> Unit) {
        thread {
            val list = mutableListOf<AppData>()
            AppUtil.getUserAppList().forEach {
                val icon = it.loadIcon(context.packageManager)
                val packageName = it.activityInfo.packageName
                val appName = it.loadLabel(context.packageManager).toString()
                list.add(AppData(packageName, icon, appName))
            }
            callback.invoke(sortList(list))
        }
    }

    fun backApp(path: String, appData: AppData, callback: (Boolean,File) -> Unit) {
        val packageInfo = AppUtil.getPackageInfo(appData.packageName)
        if (packageInfo == null) callback.invoke(false,File(""))
        AppUtil.backUserApp(path, packageInfo!!, callback::invoke)
    }

    fun startApp(context: BaseActivity, appData: AppData): Boolean {
        val packageName = appData.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        return context.startActivitySafely(intent)
    }

}