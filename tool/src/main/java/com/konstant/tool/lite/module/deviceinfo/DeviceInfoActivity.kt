package com.konstant.tool.lite.module.deviceinfo

import android.Manifest
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.DeviceInfo
import com.konstant.tool.lite.util.PermissionRequester
import kotlinx.android.synthetic.main.activity_device_info.*


/**
 * 描述:设备信息页面
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */

class DeviceInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)
        initBaseViews()
        setTitle("设备信息")
        judgePermission()
    }

    private fun judgePermission() {
        PermissionRequester.requestPermission(this,
                mutableListOf(Manifest.permission.READ_PHONE_STATE),
                { readDeviceInfo() },
                { showToast("权限申请已被拒绝，部分信息无法展示");readDeviceInfo() })
    }

    private fun readDeviceInfo() {
        val wifiInfo = DeviceInfo.getWiFiInfo(this)

        device_info.apply {
            append("\n当前连接的WiFi名字：" + wifiInfo?.ssid)
            append("\n\n当前连接的WiFi的MAC地址：" + wifiInfo?.bssid?.toUpperCase())
            append("\n\n本机MAC地址：" + DeviceInfo.getDeviceMACAddress())

            val info = packageManager.getPackageInfo(packageName, 0)
            append("\n\n当前versionName：" + info.versionName)
            append("\n\n当前versionCode：" + info.versionCode)

            val cpuModel = DeviceInfo.getCPUModel()
            append("\n\nCPU型号：$cpuModel")

            append("\n\n设备厂商：" + DeviceInfo.getDeviceFactory())

            append("\n\n手机型号：" + DeviceInfo.getDeviceType())

            append("\n\n安卓版本：" + DeviceInfo.getAndroidVersion())

            append("\n\n系统API级别：" + DeviceInfo.getDeviceAPILevel())

            append("\n\n主机地址HOST：" + DeviceInfo.getDeviceHost())

            append("\n\n设备唯一标识符：" + DeviceInfo.getDeviceFingerprint(this@DeviceInfoActivity))

            append("\n\n当前ICCID：${DeviceInfo.getCurrentIccid(this@DeviceInfoActivity)}")

            append("\n\n是否存在实体SIM卡：${DeviceInfo.isSimExist(this@DeviceInfoActivity)}")

            append("\n\nIMEI(卡一)：" + DeviceInfo.getDeviceMEIBySlotId(this@DeviceInfoActivity, 0))

            append("\n\nIMEI(卡二)：" + DeviceInfo.getDeviceMEIBySlotId(this@DeviceInfoActivity, 1))

            append("\n\nIMSI(卡一)：" + DeviceInfo.getDeviceIMSIBySlotId(this@DeviceInfoActivity, 0))

            append("\n\nIMSI(卡二)：" + DeviceInfo.getDeviceIMSIBySlotId(this@DeviceInfoActivity, 1))

            append("\n\n")
        }


    }
}
