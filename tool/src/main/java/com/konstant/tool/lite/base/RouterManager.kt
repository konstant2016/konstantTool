package com.konstant.tool.lite.base

import android.app.Activity
import android.content.Intent
import android.os.Build
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
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.module.skip.AutoSkipActivity
import com.konstant.tool.lite.module.speed.NetSpeedActivity
import com.konstant.tool.lite.module.stock.history.StockActivity
import com.konstant.tool.lite.module.translate.TranslateActivity
import com.konstant.tool.lite.module.voice.VoiceSpeechActivity
import com.konstant.tool.lite.module.wallpaper.WallpaperActivity
import com.konstant.tool.lite.module.weather.activity.WeatherActivity
import com.konstant.tool.lite.module.wxfake.WechatFakeActivity

object RouterManager {

    fun startActivity(activity: Activity, type: String) {
        val intent = when (type) {
            "TYPE_TRANSLATE" -> Intent(activity, TranslateActivity::class.java)
            "TYPE_RULER" -> Intent(activity, RulerActivity::class.java)
            "TYPE_COMPASS" -> Intent(activity, CompassActivity::class.java)
            "TYPE_EXPRESS" -> Intent(activity, ExpressListActivity::class.java)
            "NETWORK_SPEED" -> Intent(activity, NetSpeedActivity::class.java)
            "TYPE_WEATHER" -> Intent(activity, WeatherActivity::class.java)
            "DATE_CALCULATE" -> Intent(activity, DateCalculationActivity::class.java)
            "TYPE_WALLPAPER" -> Intent(activity, WallpaperActivity::class.java)
            "TYPE_CONCENTRATION" -> Intent(activity, ConcentrationActivity::class.java)
            "STOCK_CALCULATE" -> Intent(activity, StockActivity::class.java)
            "STOCK_HARDEN_ALERT" -> Intent(activity, StockActivity::class.java)
            "DOG_DIARY" -> Intent(activity, DogDiaryActivity::class.java)
            "DECIBEL_CALCULATOR" -> Intent(activity, DecibelActivity::class.java)
            "TV_LIVE" -> Intent(activity, TVLiveActivity::class.java)
            "PACKAGE_EXPORT" -> Intent(activity, PackageActivity::class.java)
            "WECHAT_SIMULATION" -> Intent(activity, WechatFakeActivity::class.java)
            "SCROLL_TEXT" -> Intent(activity, RollTextActivity::class.java)
            "DEVICE_INFORMATION" -> Intent(activity, DeviceInfoActivity::class.java)
            "DIGITAL_CLOCK" -> Intent(activity, DigitalClockActivity::class.java)
            "TYPE_QR" -> Intent(activity, QRCodeActivity::class.java)
            "VIDEO_PARSE" -> Intent(activity, ParseVideoActivity::class.java)
            "TEXT_SPEECH" -> Intent(activity, VoiceSpeechActivity::class.java)
            "SKIP_AD" -> Intent(activity, AutoSkipActivity::class.java)
            else -> Intent()
        }
        // 如果开启了适配分屏开关，则会执行这个
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && SettingManager.getAdapterSplitScreen(activity)
                && activity.isInMultiWindowMode) {
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity.startActivity(intent)
    }
}