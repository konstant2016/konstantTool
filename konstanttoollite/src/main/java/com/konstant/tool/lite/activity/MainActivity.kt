package com.konstant.tool.lite.activity

import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.eventbusparam.SwipeBackState
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.title_layout.*

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
        setTitle("菜籽工具箱-轻量版")
        setSwipeBackEnable(false)
        initBaseViews()

        layout_translate.setOnClickListener { startActivity(Intent(this@MainActivity,TranslateActivity::class.java)) }

        layout_beauty.setOnClickListener { startActivity(Intent(this@MainActivity,BeautyActivity::class.java)) }

        layout_compass.setOnClickListener { startActivity(Intent(this@MainActivity,CompassActivity::class.java)) }

        layout_qrcode.setOnClickListener { startActivity(Intent(this@MainActivity,QRCodeActivity::class.java)) }

        layout_express.setOnClickListener { startActivity(Intent(this@MainActivity,ExpressActivity::class.java)) }

        layout_device_info.setOnClickListener { startActivity(Intent(this@MainActivity,DeviceInfoActivity::class.java)) }

        layout_weather.setOnClickListener { startActivity(Intent(this@MainActivity,WeatherActivity::class.java)) }

        layout_movie.setOnClickListener { startActivity(Intent(this@MainActivity,MovieActivity::class.java)) }

        layout_ruler.setOnClickListener { startActivity(Intent(this@MainActivity,RulerActivity::class.java)) }

        layout_zfb.setOnClickListener { zfb() }

    }

    override fun initBaseViews() {
        img_back.visibility = View.GONE
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }
    }


    fun zfb(){
        val clipBoard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
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
