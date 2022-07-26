package com.konstant.tool.lite.util.choreographer

import android.app.Activity
import android.os.Looper
import android.util.Log
import android.view.Choreographer

object ChoreographerHelper {

    // 记录上次绘制的时间戳（单位：纳秒，十亿分之一秒）
    private var mLastFrameTimeNano = 0L

    // 丢帧容忍度，默认允许丢失3帧
    private const val thresholdValue = 3
    private val mStackRecorder = StackRecorder()

    fun start(activity: Activity) {
        val frameTime = getFrameTime(activity)
        val callback = object : Choreographer.FrameCallback {
            override fun doFrame(frameTimeNanos: Long) {
                if (mLastFrameTimeNano == 0L) {
                    mLastFrameTimeNano = frameTimeNanos
                    Choreographer.getInstance().postFrameCallback(this)
                    mStackRecorder.startRecord()
                    return
                }
                val diffMillSeconds = (frameTimeNanos - mLastFrameTimeNano) / 1_000_000
                if (diffMillSeconds > frameTime * thresholdValue) {
                    // 掉帧数
                    Log.e("发生了掉帧-掉帧数", "${(diffMillSeconds / frameTime).toInt()}帧")
                    printStack()
                }
                mLastFrameTimeNano = frameTimeNanos
                Choreographer.getInstance().postFrameCallback(this)
                mStackRecorder.startRecord()
            }
        }
        Choreographer.getInstance().postFrameCallback(callback)
    }

    /**
     * 根据当前屏幕刷新率获取每帧应该绘制的时间
     * 对于60Hz刷新率的屏幕，这个值应该是16.66
     * 对于90Hz刷新率的屏幕，这个值应该是11.11
     */
    private fun getFrameTime(activity: Activity): Float {
        val display = activity.windowManager.defaultDisplay
        val rate = display.refreshRate
        Log.e("发生了掉帧-每帧绘制时间", "${1000 / rate}")
        return 1000 / rate
    }

    private fun printStack(){
        mStackRecorder.printStack()
    }

}