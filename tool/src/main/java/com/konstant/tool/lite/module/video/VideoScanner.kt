package com.konstant.tool.lite.module.video

import android.media.ThumbnailUtils
import android.os.Handler
import android.provider.MediaStore
import com.konstant.tool.lite.base.KonstantApplication
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import android.provider.MediaStore.Video.Thumbnails as VideoThumbnails

/**
 * 时间：2019/1/31 11:32
 * 创建：吕卡
 * 描述：扫描本地视频文件
 */

object VideoScanner {

    enum class Type {
        STORAGE, MEDIA
    }

    private val mHandler = Handler()

    fun scanVideo(type: Type, result: (List<Video>) -> Unit, state: (index: Int, total: Int) -> Unit) {
        when (type) {
            VideoScanner.Type.STORAGE -> scanFromStorage(result)
            VideoScanner.Type.MEDIA -> scanFromMedia(result, state)
        }
    }

    private fun scanFromStorage(result: (List<Video>) -> Unit) {

    }

    private fun scanFromMedia(result: (List<Video>) -> Unit, state: (index: Int, total: Int) -> Unit) {
        thread {
            val list = ArrayList<Video>()
            val cursor = KonstantApplication.sContext.contentResolver
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
            cursor?.let {
                while (it.moveToNext()) {
                    val title = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
                    val path = it.getString(it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    val size = it.getLong(it.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
                    val duration = it.getInt(it.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
                    val bitmap = ThumbnailUtils.createVideoThumbnail(path, VideoThumbnails.MINI_KIND)
                    list.add(Video(title, path, timeConvert(duration), sizeConvert(size), bitmap))
                    mHandler.post { state(list.size, it.count) }
                }
            }
            cursor.close()
            mHandler.post { result(list) }
        }
    }

    private fun timeConvert(second: Int): String {
        if (second < 60 * 1000) {
            return "${second / 1000}秒"
        }
        if (second < 60 * 60 * 1000) {
            return "${second / (60 * 1000)}分${second % (60 * 1000) / 1000}秒"
        }
        return "${second / (60 * 60 * 1000)}时${second % (60 * 60 * 1000) / (60 * 1000)}分"
    }

    private fun sizeConvert(size: Long): String {
        val buffer = StringBuffer()
        val format = DecimalFormat("###.0")
        if (size < 1024) {
            return buffer.append(size).append("B").toString()
        }
        if (size < 1024 * 1024) {
            return buffer.append(format.format(size / (1024.0))).append("KB").toString()
        }
        if (size < 1024 * 1024 * 1024) {
            return buffer.append(format.format(size / (1024.0 * 1024.0))).append("MB").toString()
        }
        val i = (size / (1024.0 * 1024.0 * 1024.0))
        buffer.append(format.format(i)).append("GB")
        return buffer.toString()
    }

}