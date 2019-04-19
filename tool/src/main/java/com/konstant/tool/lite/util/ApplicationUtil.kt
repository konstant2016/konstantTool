package com.konstant.tool.lite.util

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import com.konstant.tool.lite.base.KonstantApplication
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object ApplicationUtil {

    // 获取应用列表
    fun getPackageInfoList(callback: (list: List<PackageInfo>) -> Unit) {
        val manager = KonstantApplication.sContext.packageManager
        val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.getInstalledPackages(PackageManager.MATCH_UNINSTALLED_PACKAGES)
        } else {
            manager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES)
        }
        callback.invoke(list)
    }

    // 获取应用包名
    fun getPackageName(packageInfo: PackageInfo) = packageInfo.packageName

    // 获取应用名字
    fun getAppName(packageInfo: PackageInfo) = packageInfo.applicationInfo.loadLabel(KonstantApplication.sContext.packageManager).toString()

    // 获取应用图标
    fun getAppIcon(packageInfo: PackageInfo): Drawable = packageInfo.applicationInfo.loadIcon(KonstantApplication.sContext.packageManager)

    // 是否为系统应用
    fun isSystemApp(packageInfo: PackageInfo): Boolean {
        val isSysApp = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0
        val isSysUpd = packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP == 1
        return isSysApp or isSysUpd
    }

    // 读取安装路径
    fun getInstallPath(packageInfo: PackageInfo) = packageInfo.applicationInfo.sourceDir

    // 备份应用
    fun backUserApp(path: String, packageInfo: PackageInfo, callback: (boolean: Boolean) -> Unit) {
        Thread {
            try {
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