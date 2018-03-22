package com.konstant.konstanttools.ui.activity.toolactivity.traffic

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.lang.ref.WeakReference

/**
 * 描述:上传到服务器的service
 * 创建人:菜籽
 * 创建时间:2018/3/1 下午4:31
 * 备注:
 */

class TrafficUploadService : Service() {

    private val mHandler = MyHandler(this)

    private class MyHandler(context: TrafficUploadService) : Handler() {
        val mContext: WeakReference<TrafficUploadService> = WeakReference(context)
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mContext.get()?.readLocalTraffic()
        }
    }

    private val mThread: Runnable = Runnable {
        run {
            mHandler.sendEmptyMessage(0)
            mHandler.postDelayed(mThread,5000)
        }
    }

    override fun onCreate() {
        super.onCreate()
        mHandler.post(mThread)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun readLocalTraffic() {
        Log.d("开始读取数据库","开始读取数据库")
        val sqLite = SQLite.instance(this)
        val cursor = sqLite.readableDatabase.query(SQLite.TABLE_NAME, null,
                null, null, null,
                null, null)

        val startIndex = cursor.getColumnIndex(SQLite.START_TIME)
        val endIndex = cursor.getColumnIndex(SQLite.END_TIME)
        val usedIndex = cursor.getColumnIndex(SQLite.USED_TRAFFIC)

        val b = cursor.moveToFirst()
        Log.d("光标",""+b)
        if (!b) return

        while (!cursor.isAfterLast) {
            val startTime = cursor.getLong(startIndex)
            val endTime = cursor.getLong(endIndex)
            val usedTraffic = cursor.getLong(usedIndex)
            sendToServer(startTime, endTime, usedTraffic)
            cursor.moveToNext()
        }
        cursor.close()
        sqLite.readableDatabase.close()

    }

    private fun sendToServer(startTime: Long, endTime: Long, usedTraffic: Long) {
        Log.d("发往服务器的数据", "startTime:$startTime,endTime:$endTime,usedTraffic:$usedTraffic")
    }

}