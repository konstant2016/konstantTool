package com.konstant.konstanttools.ui.activity.toolactivity.traffic

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.TrafficStats
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import java.lang.ref.WeakReference

/**
 * 描述:流量统计服务
 * 创建人:菜籽
 * 创建时间:2018/3/1 上午11:25
 * 备注:
 */

class TrafficCountService : Service() {

    private var timeStamp: Long = System.currentTimeMillis()    // 当前时间戳
    private var trafficStamp: Long = readLocalTraffic()         // 从系统中读取到的已用流量

    private val mAction = "com.konstant.konstanttool"


    private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val intent = Intent(this@TrafficCountService, TrafficCountService::class.java)
//            startService(intent)
        }
    }

    private open class MyHandler(context: TrafficCountService) : Handler() {

        private val mContext: WeakReference<TrafficCountService> = WeakReference(context)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            mContext.get()?.calculateTraffic()
        }

    }

    private val mHandler: MyHandler = MyHandler(this)

    private val mThread: Runnable = Runnable {
        run {
            mHandler.sendEmptyMessage(0)
            mHandler.postDelayed(mThread, 5000)
        }
    }


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val filter = IntentFilter(mAction)
        registerReceiver(mReceiver, filter)
        mHandler.post(mThread)
    }

    override fun onDestroy() {
        val intent = Intent()
        intent.action = mAction
        sendBroadcast(intent)
        unregisterReceiver(mReceiver)
        mHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    // 服务启动后，开始执行任务
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    // 读取本地已用流量
    private fun readLocalTraffic(): Long =
            TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()


    // 计算流量差值
    private fun calculateTraffic() {

        // 获取当前时间
        val currentTime = System.currentTimeMillis()

        // 读取实时流量
        val currentTraffic = readLocalTraffic()

        // 计算流量差值
        val gapTraffic = currentTraffic - trafficStamp

        // 保存当前流量
        trafficStamp = currentTraffic

        // 把流量保存到本地数据库中
        saveTraffic(timeStamp, currentTime, gapTraffic)

        // 更新读取时间
        timeStamp = currentTime

    }

    // 保存到数据库中
    private fun saveTraffic(startTime: Long, endTime: Long, usedTraffic: Long) {
        Log.d("TrafficCountService","保存数据到数据库啦")
        val sqLite = SQLite.instance(this)
        sqLite.insert(startTime,endTime,usedTraffic)
    }
}

