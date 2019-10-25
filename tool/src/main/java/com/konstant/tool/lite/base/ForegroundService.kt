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
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val enhance = intent.getBooleanExtra(ENABLE_ENHANCE, false)
        if (enhance) {
            createForegroundNotification()
        } else {
            stopForeground(true)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createForegroundNotification() {
        val channelId = "$packageName.channel"
        val intent = TaskStackBuilder.create(this)
                .addNextIntent(Intent(this@ForegroundService, SettingActivity::class.java))
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .apply {
                    setSmallIcon(R.drawable.ic_launcher)
                    setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher))
                    setAutoCancel(false)
                    setShowWhen(true)
                    setContentTitle("菜籽工具箱-后台增强服务")
                    setContentText("关闭'后台增强服务'后，此通知会自动移除")
                    setOngoing(true)
                    setContentIntent(intent)
                }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = "后台增强服务"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
        startForeground(1, notificationBuilder.build())
    }
}
