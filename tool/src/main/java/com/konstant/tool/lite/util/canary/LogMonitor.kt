package com.konstant.tool.lite.util.canary

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.util.Printer

/*
* 作者：吕卡
* 时间：2020/10/29 12:05 PM
* 描述：用于提供给Looper的信息打印
*/

class LogMonitor : Printer {

    companion object{
        fun install(){
            val printer = LogMonitor()
            Looper.getMainLooper().setMessageLogging(printer)
        }
    }

    private val mStackSampler = StackSampler(1000)

    // 卡顿阈值
    private val mBlockThresholdMillis = 3000
    private var mPrintingStarted = false
    private var mStartTimestamp = 0L
    private var mLogHandler: Handler? = null

    init {
        val handlerThread = HandlerThread("block_canary-io")
        handlerThread.start()
        mLogHandler = Handler(handlerThread.looper)
    }

    override fun println(x: String) {
        if (!mPrintingStarted) {
            mStartTimestamp = System.currentTimeMillis()
            mPrintingStarted = true
            mStackSampler.startDump()
        } else {
            val endTime = System.currentTimeMillis()
            mPrintingStarted = false
            if (endTime - mStartTimestamp > mBlockThresholdMillis) {
                onBlocked(endTime)
            }
        }
    }

    /**
     * 发生了卡顿
     */
    private fun onBlocked(endTime: Long) {
        mLogHandler?.post {
            val stacks = mStackSampler.getStacks(mStartTimestamp, endTime)
            for (stack in stacks) {
                Log.e("block-canary", stack)
            }
        }
    }

}