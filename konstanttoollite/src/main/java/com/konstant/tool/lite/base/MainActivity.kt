package com.konstant.tool.lite.base

import android.os.Bundle
import android.view.Gravity
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.setting.param.SwipeBackState
import com.konstant.tool.lite.module.beauty.activity.BeautyActivity
import com.konstant.tool.lite.module.busline.BusRouteActivity
import com.konstant.tool.lite.module.compass.CompassActivity
import com.konstant.tool.lite.module.deviceinfo.DeviceInfoActivity
import com.konstant.tool.lite.module.express.activity.ExpressListActivity
import com.konstant.tool.lite.module.qrcode.QRCodeActivity
import com.konstant.tool.lite.module.ruler.RulerActivity
import com.konstant.tool.lite.module.setting.activity.SettingActivity
import com.konstant.tool.lite.module.translate.TranslateActivity
import com.konstant.tool.lite.module.weather.activity.WeatherActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 描述:主页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {

        hideTitleBar()

        layout_translate.setOnClickListener { startActivity(TranslateActivity::class.java) }

        layout_beauty.setOnClickListener { startActivity(BeautyActivity::class.java) }

        layout_compass.setOnClickListener { startActivity(CompassActivity::class.java) }

        layout_qrcode.setOnClickListener { startActivity(QRCodeActivity::class.java) }

        layout_express.setOnClickListener { startActivity(ExpressListActivity::class.java) }

        layout_device_info.setOnClickListener { startActivity(DeviceInfoActivity::class.java) }

        layout_bus.setOnClickListener { startActivity(BusRouteActivity::class.java) }

        layout_weather.setOnClickListener { startActivity(WeatherActivity::class.java) }

        layout_ruler.setOnClickListener { startActivity(RulerActivity::class.java) }

        layout_zfb.setOnClickListener { zfb() }

        layout_side.setOnClickListener { draw_layout.openDrawer(Gravity.LEFT) }

        layout_more.setOnClickListener { startActivity(SettingActivity::class.java) }
    }


    override fun onSwipeBackChanged(msg: SwipeBackState) {

    }

}
