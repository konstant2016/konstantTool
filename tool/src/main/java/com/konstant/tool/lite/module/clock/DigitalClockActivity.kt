package com.konstant.tool.lite.module.clock

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.setting.SettingManager
import com.konstant.tool.lite.view.StatusBarUtil
import kotlinx.android.synthetic.main.activity_digital_clock.*
import java.util.*

/**
 * 时间：2021/7/31  11:21 下午
 * 作者：吕卡
 * 备注：电子时钟
 */

class DigitalClockActivity : BaseActivity() {

    private var mShowSpace = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setFullScreen(this)
        StatusBarUtil.setCutoutMode(this)
        setContentView(R.layout.activity_digital_clock)
        showTitleBar(false)
        tv_hour.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        tv_minute.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        tv_second.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        tv_mill_second.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        space_01.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        space_02.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
    }

    override fun onResume() {
        super.onResume()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        updateTime()
        val show = SettingManager.getShowMillSecond(this)
        if (show) {
            tv_mill_second.visibility = View.VISIBLE
            updateMillSecond()
        } else {
            tv_mill_second.visibility = View.GONE
            mHandler.removeCallbacks { updateMillSecond() }
        }
    }

    override fun onPause() {
        super.onPause()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun updateTime() {
        mHandler.removeCallbacks { updateTime() }
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        if (mShowSpace) {
            space_01.visibility = View.VISIBLE
            space_02.visibility = View.VISIBLE
        } else {
            space_01.visibility = View.INVISIBLE
            space_02.visibility = View.INVISIBLE
        }
        tv_hour.text = if (hour > 9) "$hour" else "0$hour"
        tv_minute.text = if (minute > 9) "$minute" else "0$minute"
        tv_second.text = if (second > 9) "$second" else "0$second"
        mShowSpace = !mShowSpace
        mHandler.postDelayed({ updateTime() }, 1000)
    }

    private fun updateMillSecond() {
        mHandler.removeCallbacks { updateMillSecond() }
        val calendar = Calendar.getInstance()
        val millSecond = calendar.get(Calendar.MILLISECOND)
        tv_mill_second.text = when {
            millSecond > 99 -> "$millSecond"
            millSecond > 9 -> "0$millSecond"
            else -> "00$millSecond"
        }
        mHandler.postDelayed({ updateMillSecond() }, 1)
    }
}