package com.konstant.tool.lite.base

import android.app.Activity
import android.content.Intent
import com.konstant.tool.lite.module.compass.CompassActivity
import com.konstant.tool.lite.module.concentration.ConcentrationActivity
import com.konstant.tool.lite.module.date.DateCalculationActivity
import com.konstant.tool.lite.module.decibel.DecibelActivity
import com.konstant.tool.lite.module.deviceinfo.DeviceInfoActivity
import com.konstant.tool.lite.module.express.activity.ExpressListActivity
import com.konstant.tool.lite.module.extract.PackageActivity
import com.konstant.tool.lite.module.live.TVLiveActivity
import com.konstant.tool.lite.module.parse.ParseVideoActivity
import com.konstant.tool.lite.module.qrcode.QRCodeActivity
import com.konstant.tool.lite.module.rolltxt.RollTextActivity
import com.konstant.tool.lite.module.ruler.RulerActivity
import com.konstant.tool.lite.module.skip.AutoSkipActivity
import com.konstant.tool.lite.module.speed.NetSpeedActivity
import com.konstant.tool.lite.module.stock.StockActivity
import com.konstant.tool.lite.module.translate.TranslateActivity
import com.konstant.tool.lite.module.voice.VoiceSpeechActivity
import com.konstant.tool.lite.module.wallpaper.WallpaperActivity
import com.konstant.tool.lite.module.weather.activity.WeatherActivity
import com.konstant.tool.lite.module.wxfake.WechatFakeActivity

object RouterManager {

    fun startActivity(activity: Activity, type: String) {
        when (type) {
            "1" -> {
                activity.startActivity(Intent(activity,TranslateActivity::class.java))
            }
            "2" -> {
                activity.startActivity(Intent(activity,RulerActivity::class.java))
            }
            "3" -> {
                activity.startActivity(Intent(activity,CompassActivity::class.java))
            }
            "4" -> {
                activity.startActivity(Intent(activity,ExpressListActivity::class.java))
            }
            "5" -> {
                activity.startActivity(Intent(activity,NetSpeedActivity::class.java))
            }
            "6" -> {
                activity.startActivity(Intent(activity,WeatherActivity::class.java))
            }
            "7" -> {
                activity.startActivity(Intent(activity,DateCalculationActivity::class.java))
            }
            "8" -> {
                activity.startActivity(Intent(activity,WallpaperActivity::class.java))
            }
            "9" -> {
                activity.startActivity(Intent(activity,ConcentrationActivity::class.java))
            }
            "10" -> {
                activity.startActivity(Intent(activity,StockActivity::class.java))
            }
            "11" -> {
                activity.startActivity(Intent(activity,DecibelActivity::class.java))
            }
            "12" -> {
                activity.startActivity(Intent(activity,TVLiveActivity::class.java))
            }
            "13" -> {
                activity.startActivity(Intent(activity,PackageActivity::class.java))
            }
            "14" -> {
                activity.startActivity(Intent(activity,WechatFakeActivity::class.java))
            }
            "15" -> {
                activity.startActivity(Intent(activity,RollTextActivity::class.java))
            }
            "16" -> {
                activity.startActivity(Intent(activity,DeviceInfoActivity::class.java))
            }
            "17" -> {
                activity.startActivity(Intent(activity,QRCodeActivity::class.java))
            }
            "18" -> {
                activity.startActivity(Intent(activity,ParseVideoActivity::class.java))
            }
            "19" -> {
                activity.startActivity(Intent(activity,VoiceSpeechActivity::class.java))
            }
            "20" -> {
                activity.startActivity(Intent(activity,AutoSkipActivity::class.java))
            }
        }
    }

}