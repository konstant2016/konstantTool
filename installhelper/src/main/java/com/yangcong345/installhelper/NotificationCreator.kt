package com.yangcong345.installhelper

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.yangcong345.install.helper.R


/**
 * 作者：konstant
 * 时间：2019/10/25 18:13
 * 描述：前台通知，用于加强应用存活性
 */

class NotificationCreator {

    companion object {

        fun createForegroundNotification(context: Context, title: String = "辅助安装服务",
                                         msg: String = "为提高成功率，请勿关闭此通知",
                                         intent: Intent = Intent(context, MainActivity::class.java)): Notification {
            val channelId = "${context.packageName}.channel"
            val targetIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            val notificationBuilder = NotificationCompat.Builder(context, channelId)
                .apply {
                    setSmallIcon(R.drawable.app_icon)
                    setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.app_icon))
                    setAutoCancel(false)
                    setShowWhen(true)
                    setContentTitle(title)
                    setContentText(msg)
                    setOngoing(true)
                    setContentIntent(targetIntent)
                }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channelName = "辅助安装服务"
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_MIN)
                val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
            return notificationBuilder.build()
        }
    }
}
