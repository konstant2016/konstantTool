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
        setTitle(getString(R.string.device_title))
        judgePermission()
    }

    private fun judgePermission() {
        PermissionRequester.requestPermission(this,
                mutableListOf(Manifest.permission.READ_PHONE_STATE),
                { readDeviceInfo() },
                { showToast(getString(R.string.device_permission_cancel));readDeviceInfo() })
    }

    private fun readDeviceInfo() {
        val wifiInfo = DeviceInfo.getWiFiInfo(this)

        device_info.apply {
            append("\n${getString(R.string.device_current_wifi_name)}：" + wifiInfo?.ssid)
            append("\n\n${getString(R.string.device_current_wifi_mac_address)}：" + wifiInfo?.bssid?.toUpperCase())
            append("\n\n${getString(R.string.device_phone_mac_address)}：" + DeviceInfo.getDeviceMACAddress())

            val info = packageManager.getPackageInfo(packageName, 0)
            append("\n\n${getString(R.string.device_current_version_name)}：" + info.versionName)
            append("\n\n${getString(R.string.device_current_version_code)}：" + info.versionCode)

            val cpuModel = DeviceInfo.getCPUModel()
            append("\n\n${getString(R.string.device_cpu_model)}：$cpuModel")

            append("\n\n${getString(R.string.device_device_factory)}：" + DeviceInfo.getDeviceFactory())

            append("\n\n${getString(R.string.device_device_model)}：" + DeviceInfo.getDeviceType())

            append("\n\n${getString(R.string.device_android_version)}：" + DeviceInfo.getAndroidVersion())

            append("\n\n${getString(R.string.device_system_api_version)}：" + DeviceInfo.getDeviceAPILevel())

            append("\n\n${getString(R.string.device_host_address)}：" + DeviceInfo.getDeviceHost())

            append("\n\n${getString(R.string.device_uuid)}：" + DeviceInfo.getDeviceFingerprint(this@DeviceInfoActivity))

            append("\n\n${getString(R.string.device_current_iccid)}：${DeviceInfo.getCurrentIccid(this@DeviceInfoActivity)}")

            append("\n\n${getString(R.string.device_sim_card_enable)}：${DeviceInfo.isSimExist(this@DeviceInfoActivity)}")

            append("\n\n${getString(R.string.device_imei_card_one)}：" + DeviceInfo.getDeviceMEIBySlotId(this@DeviceInfoActivity, 0))

            append("\n\n${getString(R.string.device_imei_card_two)}：" + DeviceInfo.getDeviceMEIBySlotId(this@DeviceInfoActivity, 1))

            append("\n\n${getString(R.string.device_imsi_card_one)}：" + DeviceInfo.getDeviceIMSIBySlotId(this@DeviceInfoActivity, 0))

            append("\n\n${getString(R.string.device_imsi_card_two)}：" + DeviceInfo.getDeviceIMSIBySlotId(this@DeviceInfoActivity, 1))

            append("\n\n")
        }


    }
}
