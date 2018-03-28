package com.konstant.konstanttools.tools

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Environment
import android.provider.Settings
import android.telephony.TelephonyManager
import java.io.File
import java.io.FileInputStream
import java.lang.reflect.Method
import java.net.NetworkInterface
import java.util.*


/**
 * 描述:封装的用于获取当前设备信息的工具类
 * 创建人:菜籽
 * 创建时间:2017/12/8 下午4:41
 * 备注:
 */

@SuppressLint("MissingPermission")
object DeviceInfoUtil {

    // 获取当前CPU型号
    fun getCPUModel(): String {
        val file = File("proc/cpuinfo")
        val inputStream = FileInputStream(file)
        val properties = Properties()
        properties.load(inputStream)
        return properties.getProperty("Hardware")
    }

    // 获取厂商信息
    fun getDeviceFactory(): String {
        return android.os.Build.MANUFACTURER
    }

    // 获取手机型号
    fun getDeviceType(): String {
        return android.os.Build.MODEL
    }

    // 获取安卓版本
    fun getAndroidVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    // 获取ICCID
    fun getCurrentIccid(context: Context): String {
        val service = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return service.simSerialNumber?:""
    }


    // 获取SIM卡状态
    @SuppressLint("ServiceCast")
    fun isSimExist(context: Context): Boolean {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.simState != (TelephonyManager.SIM_STATE_ABSENT or TelephonyManager.SIM_STATE_UNKNOWN)
    }

    // 获取主机地址
    fun getDeviceHost(): String {
        return android.os.Build.HOST
    }

    // 获取设备唯一标识符
    fun getDeviceFingerprint(context: Context): String {
        return Settings.System.getString(context.contentResolver, Settings.System.ANDROID_ID).toUpperCase()
    }

    // 获取系统API级别
    fun getDeviceAPILevel(): Int {
        return android.os.Build.VERSION.SDK_INT
    }

    // 获取当前ROM类型
    fun getROMType(): String {
        try {
            val method: Method? = Class.forName("android.os.Build")?.getMethod("hasSmartBar")
            if (method != null) return "Flyme"

            val properties = Properties()
            properties.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
            val property = properties.getProperty("ro.build.hw_emui_api_level")
            if (property != null) return "EMUI"

            val property1 = properties.getProperty("ro.miui.ui.version.name")
            if (property1 != null) return "MIUI"

            return "unknown"
        } catch (e: Exception) {

        }
        return "unknown"
    }


    // 获取当前MIUI版本
    fun getDeviceMIUIVersion(): String {
        try {
            val propFile = File(Environment.getRootDirectory(), "build.prop")
            val inputStream = FileInputStream(propFile)
            val properties = Properties()
            properties.load(inputStream)
            return properties.getProperty("ro.build.version.incremental") ?: "unknown"
        } catch (exc: Exception) {
            return "unknown"
        }

    }

    // 获取当前WIFI信息
    fun getWIFIInfo(context: Context): WifiInfo? {
        val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (manager.isWifiEnabled) manager.connectionInfo else null
    }

    // 获取本机MAC地址
    fun getDeviceMACAddress(): String {
        try {
            val all = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (nif.name != "wlan0") continue

                val macBytes = nif.getHardwareAddress() ?: return ""

                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }

                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
        }

        return "02:00:00:00:00:00"
    }


    // 获取设备指定卡槽的IMEI号码
    fun getDeviceMEIBySlotId(context: Context, slotId: Int): String {
        return try {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            manager.getDeviceId(slotId)
        } catch (ex: Exception) {
            "unknown"
        }
    }

    // 获取设备指定卡槽的IMSI号码
    fun getDeviceIMSIBySlotId(context: Context, slotId: Int): String {
        return try {
            val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val clazz: Class<*> = Class.forName("android.telephony.TelephonyManager")
            val declaredMethod = clazz.getDeclaredMethod("getSubscriberId", Int::class.java)
            declaredMethod.invoke(manager, slotId) as String
        } catch (ex: Exception) {
            "unknown"
        }
    }
}
