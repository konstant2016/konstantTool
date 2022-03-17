package com.konstant.develop.bitmap

import android.annotation.SuppressLint
import android.graphics.Bitmap
import java.util.*

@SuppressLint("NewApi")
class BitmapMemoryMonitor {

    /**
     * 用来记录所有的Bitmap内存占用情况
     * 弱引用的形式，避免引用Bitmap导致无法释放
     */
    private val bitmapHashMap = WeakHashMap<Bitmap, String>()

    fun recordBitmap(bitmap: Bitmap, id: String) {
        bitmapHashMap[bitmap] = id
    }

    /**
     * 上报当前bitmap的内存占用情况
     */
    fun reportBitmapMemory() {
        val map = mutableMapOf<String, Int>()
        bitmapHashMap.filter { it.key != null }
            .forEach {
                val bitmap = it.key
                val id = it.value
                map[id] = bitmap.allocationByteCount
            }
    }
}