package com.konstant.widget.storage

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.StatFs
import android.provider.AlarmClock
import android.provider.Settings
import android.text.format.Formatter
import android.widget.RemoteViews
import com.konstant.widget.R
import com.konstant.widget.time.TimeWidgetProvider

/**
 * 时间：2022/5/13 22:52
 * 作者：吕卡
 * 备注：桌面可用内存部件
 */

class StorageWidgetProvider : AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        updateStorage(context)
    }

    private fun updateStorage(context: Context) {
        val remoteView = RemoteViews(context.packageName, R.layout.storage_widget)
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val intent = Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        remoteView.setOnClickPendingIntent(R.id.view_parent, PendingIntent.getActivity(context,0,intent,0))

        val totalSize = totalBlocks * blockSize
        val availableSize = availableBlocks * blockSize
        val percent = ((totalSize - availableSize).toFloat() / totalSize * 100).toInt()
        val totalString = Formatter.formatFileSize(context, totalSize)
        val availableString = Formatter.formatFileSize(context, availableSize)
        val describe = "可用$availableString / 共计$totalString"
        remoteView.setTextViewText(R.id.tv_percent, "$percent")
        remoteView.setTextViewText(R.id.tv_describe, describe)
        val component = ComponentName(context, StorageWidgetProvider::class.java)
        AppWidgetManager.getInstance(context).updateAppWidget(component, remoteView)
    }

}