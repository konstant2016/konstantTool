package com.konstant.tool.lite.activity

import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.imid.swipebacklayout.lib.app.SwipeBackActivity

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

        layout_translate.setOnClickListener { startActivity(Intent(this, TranslateActivity::class.java)) }

        layout_beauty.setOnClickListener { startActivity(Intent(this, BeautyActivity::class.java)) }

        layout_compass.setOnClickListener { startActivity(Intent(this, CompassActivity::class.java)) }

        layout_qrcode.setOnClickListener { startActivity(Intent(this, QRCodeActivity::class.java)) }

        layout_express.setOnClickListener { startActivity(Intent(this, ExpressActivity::class.java)) }

        layout_device_info.setOnClickListener { startActivity(Intent(this, DeviceInfoActivity::class.java)) }

        layout_weather.setOnClickListener { startActivity(Intent(this, WeatherActivity::class.java)) }

        layout_ruler.setOnClickListener { startActivity(Intent(this, RulerActivity::class.java)) }

        layout_zfb.setOnClickListener { zfb() }

        layout_side.setOnClickListener { layout_drawer.openDrawer(Gravity.LEFT) }

        layout_more.setOnClickListener { startActivity(Intent(this, SettingActivity::class.java)) }
    }

    private fun zfb() {
        val clipBoard = this.getSystemService(SwipeBackActivity.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoard.text = "i4B6BP11Xt"
        try {
            val packageManager = this.applicationContext.packageManager
            val intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone")
            startActivity(intent)
        } catch (e: Exception) {
            val url = "https://ds.alipay.com/?from=mobileweb"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }

}
