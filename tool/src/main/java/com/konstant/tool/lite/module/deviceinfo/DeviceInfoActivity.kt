package com.konstant.tool.lite.module.deviceinfo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.DeviceInfo
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
        setTitle(getString(R.string.device_title))
        readDeviceInfo()
    }

    private fun readDeviceInfo() {
        val wifiInfo = DeviceInfo.getWiFiInfo(this)

        device_info.apply {
            append("\n\n${getString(R.string.device_current_wifi_name)}：" + wifiInfo?.ssid)
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

            append("\n\n${getString(R.string.device_screen_pixels)}：" + DeviceInfo.getScreenPixels(this@DeviceInfoActivity))

            append("\n\n${getString(R.string.device_uuid)}：" + DeviceInfo.getDeviceFingerprint(this@DeviceInfoActivity))

            append("\n\n${getString(R.string.device_sim_card_enable)}：${DeviceInfo.isSimExist(this@DeviceInfoActivity)}")

            getSystemSensor()

            append("\n\n")
        }
    }

    private fun getSystemSensor() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        device_info.append("\n\n\n设备传感器列表(共${sensorList.size}个传感器)：\n")
        sensorList.forEach {
            device_info.append("\n传感器名称：${it.name}")
            device_info.append("\n传感器厂商：${it.vendor}")
            device_info.append("\n传感器版本：${it.version}\n")
        }
    }
}
