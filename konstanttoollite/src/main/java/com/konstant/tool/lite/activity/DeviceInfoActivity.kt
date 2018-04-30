package com.konstant.tool.lite.activity

import android.Manifest
import android.os.Bundle
import android.widget.Toast
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

    private val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)
        initBaseViews()
        setTitle("设备信息")
        judgePermission()
    }


    private fun judgePermission() {
        AndPermission.with(this)
                .permission(READ_PHONE_STATE)
                .onDenied { Toast.makeText(this, "权限申请已被拒绝", Toast.LENGTH_SHORT).show() }
                .onGranted { readDeviceInfo() }
                .start()
    }


    private fun readDeviceInfo() {
        val wifiInfo = DeviceInfoUtil.getWIFIInfo(this)
        device_info.append("\n当前连接的WiFi名字：" + wifiInfo?.ssid)
        device_info.append("\n\n当前连接的WiFi的mac地址：" + wifiInfo?.bssid?.toUpperCase())
        device_info.append("\n\n本机Mac地址：" + DeviceInfoUtil.getDeviceMACAddress())

        val manager = this.packageManager
        val info = manager.getPackageInfo(this.packageName, 0)
        device_info.append("\n\n当前versionName：" + info.versionName)
        device_info.append("\n\n当前versionCode：" + info.versionCode)

        val cpuModel = DeviceInfoUtil.getCPUModel()
        device_info.append("\n\nCPU型号：" + cpuModel)

        device_info.append("\n\n设备厂商：" + DeviceInfoUtil.getDeviceFactory())

        device_info.append("\n\n手机型号：" + DeviceInfoUtil.getDeviceType())

        device_info.append("\n\n安卓版本：" + DeviceInfoUtil.getAndroidVersion())

        device_info.append("\n\n系统API级别：" + DeviceInfoUtil.getDeviceAPILevel())

        device_info.append("\n\n主机地址HOST：" + DeviceInfoUtil.getDeviceHost())

        device_info.append("\n\n设备唯一标识符：" + DeviceInfoUtil.getDeviceFingerprint(this))

        device_info.append("\n\n当前ICCID：${DeviceInfoUtil.getCurrentIccid(this)}")

        device_info.append("\n\n是否存在实体SIM卡：${DeviceInfoUtil.isSimExist(this)}")

        device_info.append("\n\nIMEI(卡一)：" + DeviceInfoUtil.getDeviceMEIBySlotId(this, 0))

        device_info.append("\n\nIMEI(卡二)：" + DeviceInfoUtil.getDeviceMEIBySlotId(this, 1))

        device_info.append("\n\nIMSI(卡一)：" + DeviceInfoUtil.getDeviceIMSIBySlotId(this, 0))

        device_info.append("\n\nIMSI(卡二)：" + DeviceInfoUtil.getDeviceIMSIBySlotId(this, 1))

        device_info.append("\n\n")
    }
}
