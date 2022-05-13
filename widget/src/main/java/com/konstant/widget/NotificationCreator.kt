package com.konstant.widget

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat


class NotificationCreator {

    companion object {

        fun createForegroundNotification(context: Context, title: String = "桌面小部件",
                                         msg: String = "桌面小部件的增强服务，移除可能会导致小部件不刷新",
                                         intent: Intent = Intent(context, MainActivity::class.java)): Notification {
            val channelId = "${context.packageName}.channel"
            val targetIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .apply {
                    setSmallIcon(R.mipmap.ic_launcher)
                    setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                    setAutoCancel(false)
                    setShowWhen(true)
                    setContentTitle(title)
                    setContentText(msg)
                    setOngoing(true)
                    setContentIntent(targetIntent)
                }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "桌面小部件的增强服务"
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN)
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
            return notificationBuilder.build()
        }
    }
}