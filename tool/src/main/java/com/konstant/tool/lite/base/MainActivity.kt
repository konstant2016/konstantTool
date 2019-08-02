package com.konstant.tool.lite.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.beauty.activity.BeautyActivity
import com.konstant.tool.lite.module.compass.CompassActivity
import com.konstant.tool.lite.module.decibel.DecibelActivity
import com.konstant.tool.lite.module.deviceinfo.DeviceInfoActivity
import com.konstant.tool.lite.module.express.activity.ExpressListActivity
import com.konstant.tool.lite.module.extract.PackageActivity
import com.konstant.tool.lite.module.parse.ParseVideoActivity
import com.konstant.tool.lite.module.qrcode.QRCodeActivity
import com.konstant.tool.lite.module.rolltxt.RollTextActivity
import com.konstant.tool.lite.module.ruler.RulerActivity
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.setting.activity.SettingActivity
import com.konstant.tool.lite.module.setting.param.SwipeBackStatus
import com.konstant.tool.lite.module.speed.NetSpeedActivity
import com.konstant.tool.lite.module.translate.TranslateActivity
import com.konstant.tool.lite.module.weather.activity.WeatherActivity
import com.konstant.tool.lite.module.wxfake.WechatFakeActivity
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_drawer_left.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述:主页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class MainActivity : BaseActivity() {

    private val TAG = "MainActivity";

    private var mLastTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("菜籽工具箱")
        setContentView(R.layout.activity_main)
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {

        img_back.visibility = View.GONE
        img_more.visibility = View.GONE
        img_drawer.visibility = View.VISIBLE
        img_setting.visibility = View.VISIBLE

        layout_translate.setOnClickListener { startActivity(TranslateActivity::class.java) }

        layout_beauty.setOnClickListener { startActivity(BeautyActivity::class.java) }

        layout_compass.setOnClickListener { startActivity(CompassActivity::class.java) }

        layout_qrcode.setOnClickListener { startActivity(QRCodeActivity::class.java) }

        layout_express.setOnClickListener { startActivity(ExpressListActivity::class.java) }

        layout_speed.setOnClickListener { startActivity(NetSpeedActivity::class.java) }

        layout_device_info.setOnClickListener { startActivity(DeviceInfoActivity::class.java) }

        layout_weather.setOnClickListener { startActivity(WeatherActivity::class.java) }

        layout_decibel.setOnClickListener { startActivity(DecibelActivity::class.java) }

        layout_ruler.setOnClickListener { startActivity(RulerActivity::class.java) }

        layout_package.setOnClickListener { startActivity(PackageActivity::class.java) }

        layout_wxfake.setOnClickListener { startActivity(WechatFakeActivity::class.java) }

        layout_roll.setOnClickListener { startActivity(RollTextActivity::class.java) }

        layout_vip.setOnClickListener { startActivity(ParseVideoActivity::class.java) }

        img_drawer.setOnClickListener { draw_layout.openDrawer(Gravity.LEFT) }

        img_setting.setOnClickListener { startActivity(SettingActivity::class.java) }
    }


    override fun onSwipeBackChanged(msg: SwipeBackStatus) {

    }

    override fun onBackPressed() {
        if (draw_layout.isDrawerOpen(layout_left)) {
            draw_layout.closeDrawer(Gravity.LEFT)
            return
        }
        if (SettingManager.getExitTipsStatus(this) and (System.currentTimeMillis() - mLastTime > 2000)) {
            mLastTime = System.currentTimeMillis()
            showToast("再按一次返回键退出应用")
            return
        }
        if (SettingManager.getKillProcess(this)) {
            finish()
            Handler().postDelayed({ android.os.Process.killProcess(android.os.Process.myPid()) }, 100)
        } else {
            Intent(Intent.ACTION_MAIN).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                addCategory(Intent.CATEGORY_HOME)
                startActivity(this)
            }
        }
    }
}
