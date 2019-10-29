package com.konstant.tool.lite.base

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.setting.activity.SettingActivity

/**
 * 作者：konstant
 * 时间：2019/10/25 18:13
 * 描述：前台通知，用于加强应用存活性
 */

class ForegroundService : Service() {

    companion object {
        private const val ENABLE_ENHANCE = "enable_enhance"
        fun startForegroundService(context: Context, enhance: Boolean) {
            with(Intent(context, ForegroundService::class.java)) {
                putExtra(ENABLE_ENHANCE, enhance)
                context.startService(this)
            }
        }

        fun createForegroundNotification(context: Context, title: String = "菜籽工具箱-后台增强服务",
                                         msg: String = "关闭'后台增强服务'后，此通知会自动移除"): Notification {
            val channelId = "${context.packageName}.channel"
            val intent = TaskStackBuilder.create(context)
                    .addNextIntent(Intent(context, SettingActivity::class.java))
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                    .apply {
                        setSmallIcon(R.drawable.ic_launcher)
                        setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                        setAutoCancel(false)
                        setShowWhen(true)
                        setContentTitle(title)
                        setContentText(msg)
                        setOngoing(true)
                        setContentIntent(intent)
                    }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "后台增强服务"
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN)
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
            return notificationBuilder.build()
        }
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val enhance = intent.getBooleanExtra(ENABLE_ENHANCE, false)
        if (enhance) {
            startForeground(1, Companion.createForegroundNotification(this))
        } else {
            stopForeground(true)
        }
        return super.onStartCommand(intent, flags, startId)
    }

}
