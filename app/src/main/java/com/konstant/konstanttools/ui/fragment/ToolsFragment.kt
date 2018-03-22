package com.konstant.konstanttools.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseFragment
import com.konstant.konstanttools.ui.activity.SMSFakeActivity
import com.konstant.konstanttools.ui.activity.toolactivity.DeviceInfoActivity
import com.konstant.konstanttools.ui.activity.toolactivity.CompassActivity
import com.konstant.konstanttools.ui.activity.toolactivity.PhoneLocationActivity
import com.konstant.konstanttools.ui.activity.toolactivity.TranslateActivity
import com.konstant.konstanttools.ui.activity.toolactivity.beauty.BeautyActivity
import com.konstant.konstanttools.ui.activity.toolactivity.im.activity.IMActivity
import com.konstant.konstanttools.ui.activity.toolactivity.im.activity.LoginActivity
import com.konstant.konstanttools.ui.activity.toolactivity.qrcode.QRCodeActivity
import com.konstant.konstanttools.ui.activity.toolactivity.traffic.TrafficActivity
import com.konstant.konstanttools.ui.activity.toolactivity.traffic.TrafficCountService
import com.konstant.konstanttools.ui.activity.toolactivity.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_tools.*

/**
 * 描述:一些小工具
 * 创建人:菜籽
 * 创建时间:2017/12/28 下午6:26
 * 备注:
 */


class ToolsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_tools, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout_translate.setOnClickListener { startActivity(Intent(mActivity, TranslateActivity::class.java)) }

        // 指南针
        layout_compass.setOnClickListener { startActivity(Intent(mActivity, CompassActivity::class.java)) }

        // 号码归属地
        layout_phone_location.setOnClickListener { startActivity(Intent(mActivity, PhoneLocationActivity::class.java)) }

        // 天气查询
        layout_weather.setOnClickListener { startActivity(Intent(mActivity, WeatherActivity::class.java)) }

        // 二维码扫描
        layout_qr_code.setOnClickListener { startActivity(Intent(mActivity, QRCodeActivity::class.java)) }

        // 美女图片
        beauty.setOnClickListener { startActivity(Intent(mActivity, BeautyActivity::class.java)) }

        // 环信即时通信
//        layout_im.setOnClickListener { startActivity(Intent(mActivity, LoginActivity::class.java)) }
        layout_im.setOnClickListener { startActivity(Intent(mActivity, IMActivity::class.java)) }

        // 获取设备信息
        deviceInfo.setOnClickListener { startActivity(Intent(mActivity, DeviceInfoActivity::class.java)) }

        // 短信伪造
        layout_sms.setOnClickListener { startActivity(Intent(mActivity, SMSFakeActivity::class.java)) }

        // 流量统计服务
        traffic_count.setOnClickListener { startActivity(Intent(mActivity,TrafficActivity::class.java)) }
    }

}