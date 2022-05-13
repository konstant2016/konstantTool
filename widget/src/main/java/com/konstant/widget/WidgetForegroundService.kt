package com.konstant.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

/**
 * 时间：2022/5/13 23:43
 * 作者：吕卡
 * 备注：用来处理组件的刷新逻辑，应用启动后，启动这个服务，然后接收时间的广播，用来刷新页面数据
 */

class WidgetForegroundService : Service() {

    companion object {
        fun startForegroundService(context: Context) {
            val intent = Intent(context, WidgetForegroundService::class.java)
            context.startService(intent)
        }
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            this@WidgetForegroundService.sendBroadcast(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendNotification()
        val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
        registerReceiver(receiver, intentFilter)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun sendNotification() {
        val notification = NotificationCreator.createForegroundNotification(this)
        startForeground(1, notification)
    }
}