package com.konstant.tool.lite.module.deviceinfo

import android.Manifest
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.DeviceInfoUtil
import com.yanzhenjie.permission.AndPermission
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
        AndPermission.with(this)
                .permission(Manifest.permission.READ_PHONE_STATE)
                .onDenied { showToast("权限申请已被拒绝") }
                .onGranted { readDeviceInfo() }
                .start()
    }


    private fun readDeviceInfo() {
        val act = this
        val wifiInfo = DeviceInfoUtil.getWIFIInfo(this)

        device_info.apply {
            append("\n当前连接的WiFi名字：" + wifiInfo?.ssid)
            append("\n\n当前连接的WiFi的mac地址：" + wifiInfo?.bssid?.toUpperCase())
            append("\n\n本机Mac地址：" + DeviceInfoUtil.getDeviceMACAddress())

            val manager = act.packageManager
            val info = manager.getPackageInfo(act.packageName, 0)
            append("\n\n当前versionName：" + info.versionName)
            append("\n\n当前versionCode：" + info.versionCode)

            val cpuModel = DeviceInfoUtil.getCPUModel()
            append("\n\nCPU型号：$cpuModel")

            append("\n\n设备厂商：" + DeviceInfoUtil.getDeviceFactory())

            append("\n\n手机型号：" + DeviceInfoUtil.getDeviceType())

            append("\n\n安卓版本：" + DeviceInfoUtil.getAndroidVersion())

            append("\n\n系统API级别：" + DeviceInfoUtil.getDeviceAPILevel())

            append("\n\n主机地址HOST：" + DeviceInfoUtil.getDeviceHost())

            append("\n\n设备唯一标识符：" + DeviceInfoUtil.getDeviceFingerprint(act))

            append("\n\n当前ICCID：${DeviceInfoUtil.getCurrentIccid(act)}")

            append("\n\n是否存在实体SIM卡：${DeviceInfoUtil.isSimExist(act)}")

            append("\n\nIMEI(卡一)：" + DeviceInfoUtil.getDeviceMEIBySlotId(act, 0))

            append("\n\nIMEI(卡二)：" + DeviceInfoUtil.getDeviceMEIBySlotId(act, 1))

            append("\n\nIMSI(卡一)：" + DeviceInfoUtil.getDeviceIMSIBySlotId(act, 0))

            append("\n\nIMSI(卡二)：" + DeviceInfoUtil.getDeviceIMSIBySlotId(act, 1))

            append("\n\n")
        }


    }
}