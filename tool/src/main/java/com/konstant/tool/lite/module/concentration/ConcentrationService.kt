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
import kotlinx.android.synthetic.main.layout_concetration.view.*
import java.lang.Exception

class ConcentrationService : Service() {

    companion object {
        val TYPE = "type"
        val MINUTES = "minutes"

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

    private val mView by lazy { LayoutInflater.from(this).inflate(R.layout.layout_concetration, null) }

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
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val params = WindowManager.LayoutParams()
                .apply {
                    type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                    } else {
                        WindowManager.LayoutParams.TYPE_PHONE
                    }
                    format = PixelFormat.TRANSLUCENT
                    flags = (WindowManager.LayoutParams.FLAG_FULLSCREEN       // 全屏
                            or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)    // 可放到状态栏上
                    width = WindowManager.LayoutParams.MATCH_PARENT
                    height = WindowManager.LayoutParams.MATCH_PARENT
                }
        removeView()
        windowManager.addView(mView, params)
        updateCountdown(minutes, mView.tv_time)
        val notification = NotificationCreator.createForegroundNotification(this, msg = "'专注模式'结束后，此通知自动移除")
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
                Toast.makeText(this@ConcentrationService, "专注模式已结束", Toast.LENGTH_SHORT).show()
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
