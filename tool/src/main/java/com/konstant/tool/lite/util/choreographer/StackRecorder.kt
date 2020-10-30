package com.konstant.tool.lite.util.choreographer

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList


class StackRecorder {

    private val stackList = Collections.synchronizedList(ArrayList<String>())

    private val mHandler by lazy {
        val thread = HandlerThread("chore-canary")
        thread.start()
        Handler(thread.looper)
    }


    //子线程，用于记录堆栈信息
    private val mRunnable = {
        val stackTrace = Looper.getMainLooper().thread.stackTrace
        synchronized(stackList){
            stackTrace.forEach {
                stackList.add( it.toString())
                if (stackList.size == 100) {
                    stackList.removeAt(0)
                }
            }
            recordStack()
        }
    }

    fun startRecord() {
        stackList.clear()
        recordStack()
    }

    private fun recordStack() {
        mHandler.post(mRunnable)
    }

    //打印堆栈信息
    fun printStack() {
        mHandler.removeCallbacks(mRunnable)
        mHandler.post {
            synchronized(stackList){
                for (i in 0 until stackList.size){
                    Log.e("chore-canary", "${stackList[i]}")
                }
            }
        }
    }
}