package com.konstant.tool.lite.base

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.setting.activity.SettingActivity

/**
 * 作者：konstant
 * 时间：2019/10/25 18:13
 * 描述：前台通知，用于加强应用存活性
 */

class NotificationCreator {

    companion object {

        fun createForegroundNotification(context: Context, title: String = context.getString(R.string.notification_creator_title),
                                         msg: String = context.getString(R.string.notification_creator_message)): Notification {
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
                val channelName = context.getString(R.string.notification_creator_channel_name)
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN)
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
            return notificationBuilder.build()
        }
    }
}
