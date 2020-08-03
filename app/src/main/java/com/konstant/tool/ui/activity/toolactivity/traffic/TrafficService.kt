package com.konstant.tool.ui.activity.toolactivity.traffic

import android.app.Service
import android.content.Intent
import android.net.TrafficStats
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import java.lang.ref.WeakReference

/**
 * 描述:合成读取流量和上传到服务器的流量，合成一个服务
 * 创建人:菜籽
 * 创建时间:2018/3/1 下午5:47
 * 备注:
 */

class TrafficService : Service() {


    private var timeStamp: Long = System.currentTimeMillis()    // 当前时间戳
    private var trafficStamp: Long = readUsedTraffic()         // 从系统中读取到的已用流量

    private val MESSAGE_COUNT = 0       // 用于统计流量的标记
    private val MESSAGE_UPLOAD = 1      // 用于上传流量的标记

    private var COUNT_GAP: Long = 5000        // 流量统计的时间间隔
    private var UPLOAD_GAP: Long = 10000      // 上传流量的时间间隔

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private class MyHandler(context: TrafficService) : Handler() {
        private val mContext = WeakReference(context)
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
            // 统计流量
                mContext.get()?.MESSAGE_COUNT ->
                    mContext.get()?.calculateTraffic()
            // 上传流量
                mContext.get()?.MESSAGE_UPLOAD ->
                    mContext.get()?.readLocalTraffic()
            }
        }
    }

    private val mHandler = MyHandler(this)

    // 计算流量的线程
    private val mThreadCount: Runnable = Runnable {
        var message = Message.obtain()
        message.what = MESSAGE_COUNT
        run {
            mHandler.sendMessage(message)
            mHandler.postDelayed(mThreadCount, COUNT_GAP)
        }
    }

    // 上传流量的线程
    private val mThreadUpload: Runnable = Runnable {
        val message = Message.obtain()
        message.what = MESSAGE_UPLOAD
        run {
            mHandler.sendMessage(message)
            mHandler.postDelayed(mThreadUpload, UPLOAD_GAP)
        }
    }

    // 执行指令
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        COUNT_GAP = intent.getLongExtra("countGap", 5000)
        UPLOAD_GAP = intent.getLongExtra("uploadGap", 10000)
        mHandler.post(mThreadCount)
        mHandler.post(mThreadUpload)
        Toast.makeText(this, "流量统计已开启", Toast.LENGTH_SHORT).show()
        return START_STICKY
    }

    override fun onDestroy() {
        mHandler.removeCallbacksAndMessages(null)
        Toast.makeText(this, "流量统计已关闭", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }

    // 读取本地已用流量
    private fun readUsedTraffic(): Long =
            TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()

    // 计算流量差值
    private fun calculateTraffic() {
        // 获取当前时间
        val currentTime = System.currentTimeMillis()
        // 读取实时流量
        val currentTraffic = readUsedTraffic()
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
        Log.d("TrafficCountService", "保存数据到数据库啦")
        val sqLite = SQLite.instance(this)
        sqLite.insert(startTime, endTime, usedTraffic)
    }


    // 读取本地数据库保存的流量
    fun readLocalTraffic() {
        Log.d("开始读取数据库", "开始读取数据库")
        val sqLite = SQLite.instance(this)
        val cursor = sqLite.readableDatabase.query(SQLite.TABLE_NAME, null,
                null, null, null,
                null, null, "10")

        val startIndex = cursor.getColumnIndex(SQLite.START_TIME)
        val endIndex = cursor.getColumnIndex(SQLite.END_TIME)
        val usedIndex = cursor.getColumnIndex(SQLite.USED_TRAFFIC)

        if (cursor.count < 10) {
            cursor.close()
            sqLite.readableDatabase.close()
            return
        }

        val b = cursor.moveToFirst()
        Log.d("光标", "" + b)
        if (!b) return

        val ids = mutableListOf<Map<String, Long>>()

        while (!cursor.isAfterLast) {

            val startTime = cursor.getLong(startIndex)
            val endTime = cursor.getLong(endIndex)
            val usedTraffic = cursor.getLong(usedIndex)
            cursor.moveToNext()
            val trafficMap = mapOf<String, Long>(Pair("bt", startTime),
                    Pair("et", endTime), Pair("u", usedTraffic))

            ids.add(trafficMap)
        }

        sendToServer(ids)
    }

    // 上传到服务器
    private fun sendToServer(data: MutableList<Map<String, Long>>) {
        val jsonObject = JSONObject()
        jsonObject.put("iccid",DeviceInfo.readCurrentIccid(this))
        jsonObject.put("imei",DeviceInfo.readIMEI(this))
        jsonObject.put("data",data)
        jsonObject.put("mid","${DeviceInfo.readIMEI(this)}${System.currentTimeMillis()}")

        Log.d("s",JSON.toJSONString(jsonObject))
        val bytes = SnappyTool.compressString(JSON.toJSONString(jsonObject))

        Upload.getInstance().uploadByteArray(bytes){
            state, _ ->
            if (state) deleteItem(data)
        }
    }

    // 删除指令的数据条目
    private fun deleteItem(data: MutableList<Map<String, Long>>) {
        data.forEach {
            Log.d("发往服务器的数据", "userId" + it["userId"] +
                    "startTime" + it["startTime"] + "endTime" + it["endTime"] +
                    "usedTraffic" + it["usedTraffic"])
            val uid = arrayOf(it["userId"].toString())
            SQLite.instance(this).delete(uid)

        }
    }

}