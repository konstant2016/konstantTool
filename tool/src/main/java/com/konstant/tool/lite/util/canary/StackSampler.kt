package com.konstant.tool.lite.util.canary

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicBoolean

/*
* 作者：吕卡
* 时间：2020/10/29 12:08 PM
* 描述：用于记录堆栈信息
*/

class StackSampler(private val sampleInterval: Long /**采样频率*/) {

    private var mHandler: Handler? = null
    private val MAX_COUNT = 100

    // 用来记录任务栈的内容
    private val mStackMap = LinkedHashMap<Long, String>()


    // 用户记录当前时间段内的堆栈信息的任务
    private val mRunnable = Runnable {
        val sb = StringBuffer()
        val stackTrace = Looper.getMainLooper().thread.stackTrace
        stackTrace.forEach {
            sb.append(it.toString()).append("\n")
        }
        synchronized(StackSampler::class) {
            if (mStackMap.size == MAX_COUNT) {
                mStackMap.remove(mStackMap.keys.iterator().next())
            }
            mStackMap.put(System.currentTimeMillis(), sb.toString())
        }
        if (mShouldSample.get()) {
            sendMessage()
        }
    }

    init {
        val handlerThread = HandlerThread("block-canary-sampler")
        handlerThread.start()
        mHandler = Handler(handlerThread.looper)
    }

    private val mShouldSample = AtomicBoolean(false)

    fun startDump() {
        if (mShouldSample.get()) {
            return
        }
        mShouldSample.set(true)
        mHandler?.removeCallbacks(mRunnable)
        sendMessage()
    }

    fun stopDump() {
        if ((!mShouldSample.get())) {
            return
        }
        mShouldSample.set(false)
        mHandler?.removeCallbacks(mRunnable)
    }

    fun getStacks(startTime: Long, endTime: Long): List<String> {
        synchronized(mStackMap){
            val result = mutableListOf<String>()
            mStackMap.forEach {
                val time = it.key
                val string = it.value
                if (time in (startTime + 1) until endTime) {
                    val timeString = SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(time)
                    result.add("$timeString \r\n\r\n $string")
                }
            }
            return result
        }
    }

    private fun sendMessage() {
        mHandler?.postDelayed(mRunnable, sampleInterval)
    }

}