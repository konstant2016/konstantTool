package com.konstant.tool.ui.activity.toolactivity.traffic

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager

/**
 * 描述:获取当前设备信息
 * 创建人:菜籽
 * 创建时间:2018/3/16 下午4:17
 * 备注:
 */

object DeviceInfo {

    // 获取IMEI号码
    @SuppressLint("ServiceCast", "MissingPermission")
    fun readIMEI(context: Context): String {
        val manager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return manager.deviceId
    }

    // 获取ICCID
    @SuppressLint("MissingPermission")
    fun readCurrentIccid(context: Context):String{
        val service = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return service.simSerialNumber
    }

}