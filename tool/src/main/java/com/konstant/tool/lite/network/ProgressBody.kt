package com.konstant.tool.lite.network

import okhttp3.ResponseBody
import okio.*

/**
* 时间：2019/5/5 17:14
* 创建：吕卡
* 描述：下载进度监听，下载大文件时不用此方法
*/

class ProgressBody(val responseBody: ResponseBody, val progressListener: ProgressListener?) : ResponseBody() {

    private lateinit var bufferedSource: BufferedSource

    interface ProgressListener {
        fun update(current: Long, length: Long, done: Boolean)
    }

    override fun contentLength() = responseBody.contentLength()

    override fun contentType() = responseBody.contentType()

    override fun source(): BufferedSource {
        bufferedSource = Okio.buffer(source(responseBody.source()))
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = super.read(sink, byteCount)
                if (bytesRead != -1L) {
                    totalBytesRead += bytesRead
                }
                //接口回调
                progressListener?.update(totalBytesRead, responseBody.contentLength(), bytesRead == -1L)
                return bytesRead
            }
        }
    }
}