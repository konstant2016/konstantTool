package com.konstant.tool.lite.network.config

import com.konstant.tool.lite.network.api.SpeedApi
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

/**
 * 作者：konstant
 * 时间：2019/10/24 11:42
 * 描述：大型文件下载，带有进度回调
 */

object FileDownloader {

    interface DownloadListener {
        fun onStart() {}
        fun onProgress(current: Long, total: Long) {}
        fun onFinish() {}
        fun onError(msg: String) {}
    }

    // DownloadListener 的代理，用来切换到UI线程，回调结果
    class MainThreadDownloadListener(private val listener: DownloadListener) : DownloadListener by listener {
        override fun onStart() {
            threadChange { listener.onStart() }
        }

        override fun onProgress(current: Long, total: Long) {
            threadChange { listener.onProgress(current, total) }
        }

        override fun onFinish() {
            threadChange { listener.onFinish() }
        }

        override fun onError(msg: String) {
            threadChange { listener.onError(msg) }
        }

        private fun threadChange(method: () -> Unit) {
            val disposable = Observable.create(ObservableOnSubscribe<String> { emitter -> emitter.onNext("") })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { method.invoke() }
        }
    }

    fun downloadFile(maxSize: Long = -1, path: String, listener: DownloadListener) {
        val mainThreadListener = MainThreadDownloadListener(listener)
        val call = RetrofitBuilder.getApi(SpeedApi.HOST, SpeedApi::class.java).getDownload()
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
                mainThreadListener.onError("网络错误")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val inputStream = response.body()!!.byteStream()
                    val length = response.body()!!.contentLength()
                    val outputStream = FileOutputStream(File(path))
                    val bytes = ByteArray(1024)
                    var ch: Int
                    var process = 0L
                    while (inputStream.read(bytes).also { ch = it } != -1) {
                        outputStream.write(bytes)
                        process += ch
                        mainThreadListener.onProgress(process, length)
                        if (maxSize != -1L && process >= maxSize) break
                    }
                    outputStream.flush()
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    mainThreadListener.onError("骚瑞，挂了，我也不晓得为撒子")
                }
            }
        })
    }

}