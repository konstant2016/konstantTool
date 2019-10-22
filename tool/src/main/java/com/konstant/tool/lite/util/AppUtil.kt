package com.konstant.tool.lite.util

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.Log
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.KonApplication
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object AppUtil {

    // 获取应用列表
    fun getPackageInfoList(): List<PackageInfo> {
        val manager = KonApplication.context.packageManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES)
        } else {
            manager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
        }
    }

    // 获取packageInfo
    fun getPackageInfo(packageName: String): PackageInfo? {
        getPackageInfoList().forEach {
            if (it.packageName == packageName) {
                return it
            }
        }
        return null
    }

    // 获取应用包名
    fun getPackageName(packageInfo: PackageInfo) = packageInfo.packageName

    // 获取应用名字
    fun getAppName(packageInfo: PackageInfo) = packageInfo.applicationInfo.loadLabel(KonApplication.context.packageManager).toString()

    // 获取应用图标
    fun getAppIcon(packageInfo: PackageInfo): Drawable = packageInfo.applicationInfo.loadIcon(KonApplication.context.packageManager)

    // 是否为系统应用
    fun isSystemApp(packageInfo: PackageInfo): Boolean {
        val isSysApp = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        val isSysUpd = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP == 1
        return isSysApp or isSysUpd
    }

    fun isTop(activity: BaseActivity): Boolean {
        val manager = activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = manager.getRunningTasks(1)
        if (tasks == null || tasks.size == 0) return false
        return TextUtils.equals(activity.componentName.className, tasks[0].topActivity!!.className)
    }

    // 获取可以直接跳转的应用列表
    fun getUserAppList(): List<ResolveInfo> {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        return KonApplication.context.packageManager.queryIntentActivities(intent, 0)
    }

    // 读取安装路径
    fun getInstallPath(packageInfo: PackageInfo) = packageInfo.applicationInfo.sourceDir

    // 备份应用
    fun backUserApp(path: String, packageInfo: PackageInfo, callback: (boolean: Boolean) -> Unit) {
        Thread {
            try {
                if (!File(path).exists()) {
                    File(path).mkdir()
                }
                val name = getAppName(packageInfo)
                val outFile = File("$path${File.separator}$name.apk")
                val installFile = File(getInstallPath(packageInfo))

                val installChannel = FileInputStream(installFile).channel
                val outChannel = FileOutputStream(outFile).channel
                outChannel.transferFrom(installChannel, 0, installChannel.size())

                callback.invoke(true)
            } catch (e: Exception) {
                Log.d("备份", "失败")
                e.printStackTrace()
                callback.invoke(false)
            }
        }.start()
    }
}