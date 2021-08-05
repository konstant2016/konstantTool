package com.konstant.tool.lite.base

import android.app.Activity
import android.content.Intent
import com.konstant.tool.lite.module.clock.DigitalClockActivity
import com.konstant.tool.lite.module.compass.CompassActivity
import com.konstant.tool.lite.module.concentration.ConcentrationActivity
import com.konstant.tool.lite.module.date.DateCalculationActivity
import com.konstant.tool.lite.module.decibel.DecibelActivity
import com.konstant.tool.lite.module.deviceinfo.DeviceInfoActivity
import com.konstant.tool.lite.module.diary.DogDiaryActivity
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
            "TYPE_TRANSLATE" -> {
                activity.startActivity(Intent(activity, TranslateActivity::class.java))
            }
            "TYPE_RULER" -> {
                activity.startActivity(Intent(activity, RulerActivity::class.java))
            }
            "TYPE_COMPASS" -> {
                activity.startActivity(Intent(activity, CompassActivity::class.java))
            }
            "TYPE_EXPRESS" -> {
                activity.startActivity(Intent(activity, ExpressListActivity::class.java))
            }
            "NETWORK_SPEED" -> {
                activity.startActivity(Intent(activity, NetSpeedActivity::class.java))
            }
            "TYPE_WEATHER" -> {
                activity.startActivity(Intent(activity, WeatherActivity::class.java))
            }
            "DATE_CALCULATE" -> {
                activity.startActivity(Intent(activity, DateCalculationActivity::class.java))
            }
            "TYPE_WALLPAPER" -> {
                activity.startActivity(Intent(activity, WallpaperActivity::class.java))
            }
            "TYPE_CONCENTRATION" -> {
                activity.startActivity(Intent(activity, ConcentrationActivity::class.java))
            }
            "STOCK_CALCULATE" -> {
                activity.startActivity(Intent(activity, StockActivity::class.java))
            }
            "DOG_DIARY" -> {
                activity.startActivity(Intent(activity, DogDiaryActivity::class.java))
            }
            "DECIBEL_CALCULATOR" -> {
                activity.startActivity(Intent(activity, DecibelActivity::class.java))
            }
            "TV_LIVE" -> {
                activity.startActivity(Intent(activity, TVLiveActivity::class.java))
            }
            "PACKAGE_EXPORT" -> {
                activity.startActivity(Intent(activity, PackageActivity::class.java))
            }
            "WECHAT_SIMULATION" -> {
                activity.startActivity(Intent(activity, WechatFakeActivity::class.java))
            }
            "SCROLL_TEXT" -> {
                activity.startActivity(Intent(activity, RollTextActivity::class.java))
            }
            "DEVICE_INFORMATION" -> {
                activity.startActivity(Intent(activity, DeviceInfoActivity::class.java))
            }
            "DIGITAL_CLOCK" -> {
                activity.startActivity(Intent(activity, DigitalClockActivity::class.java))
            }
            "TYPE_QR" -> {
                activity.startActivity(Intent(activity, QRCodeActivity::class.java))
            }
            "VIDEO_PARSE" -> {
                activity.startActivity(Intent(activity, ParseVideoActivity::class.java))
            }
            "TEXT_SPEECH" -> {
                activity.startActivity(Intent(activity, VoiceSpeechActivity::class.java))
            }
            "SKIP_AD" -> {
                activity.startActivity(Intent(activity, AutoSkipActivity::class.java))
            }
        }
    }
}