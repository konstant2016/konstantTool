package com.konstant.tool.lite.module.concentration

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Typeface
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.NotificationCreator
import com.konstant.tool.lite.module.setting.SettingManager
import kotlinx.android.synthetic.main.activity_setting.view.*
import kotlinx.android.synthetic.main.layout_concentration.view.*
import java.lang.Exception

/**
 * 描述：专注模式的Service
 * 创建者：吕卡
 * 时间：2020/7/23:2:35 PM
 */

class ConcentrationService : Service() {

    companion object {
        const val TYPE = "type"
        const val MINUTES = "minutes"

        fun startCountDown(context: Context, minutes: Int) {
            val intent = Intent(context, ConcentrationService::class.java)
            intent.putExtra(MINUTES, minutes)
            intent.putExtra(TYPE, 1)
            context.startService(intent)
        }

        fun stopCountDown(context: Context) {
            val intent = Intent(context, ConcentrationService::class.java)
            intent.putExtra(TYPE, 2)
            context.startService(intent)
        }
    }

    private val mView by lazy { LayoutInflater.from(this).inflate(R.layout.layout_concentration, null) }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.getIntExtra(TYPE, 0)) {
            1 -> {
                val minutes = intent.getIntExtra(MINUTES, 30)
                showFloatView(minutes)
            }
            2 -> {

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun showFloatView(minutes: Int) {
        setTheme(SettingManager.getTheme(this))
        SettingManager.saveKillProcess(this, false)
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams()
                .apply {
                    type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        WindowManager.LayoutParams.TYPE_PHONE
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                    }
                    format = PixelFormat.TRANSLUCENT
                    flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN       // 全屏
                            or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)    // 可放到状态栏上
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.MATCH_PARENT
                }
        removeView()
        mView.layout_root_view.setOnClickListener {
            Toast.makeText(this, R.string.concentration_can_not_operation, Toast.LENGTH_SHORT).show()
        }
        windowManager.addView(mView, params)
        updateCountdown(minutes, mView.tv_time)
        val notification = NotificationCreator.createForegroundNotification(this, msg = getString(R.string.concentration_notification_describe))
        startForeground(1, notification)
    }

    private fun removeView() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        try {
            windowManager.removeView(mView)
        } catch (exception: Exception) {
            //
        }
    }

    private fun updateCountdown(minutes: Int, textView: TextView) {
        textView.typeface = Typeface.createFromAsset(assets, "LCDTimeDate.ttf")
        object : CountDownTimer(minutes.toLong() * 60 * 1000, 1000) {
            override fun onFinish() {
                removeView()
                Toast.makeText(this@ConcentrationService, R.string.concentration_cancel_success, Toast.LENGTH_SHORT).show()
                stopForeground(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 1000 / 60 / 60
                val minute = (millisUntilFinished - hour * 60 * 60 * 1000) / 1000 / 60
                val second = millisUntilFinished / 1000 % 60
                val stringBuilder = StringBuilder()
                stringBuilder.append(if (hour > 9) "$hour" else "0$hour")
                        .append(":")
                        .append(if (minute > 9) "$minute" else "0$minute")
                        .append(":")
                        .append(if (second > 9) "$second" else "0$second")
                textView.text = stringBuilder.toString()
            }
        }.start()
    }
}
