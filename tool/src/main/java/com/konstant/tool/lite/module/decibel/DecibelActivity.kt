package com.konstant.tool.lite.module.decibel

import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.util.PermissionRequester
import kotlinx.android.synthetic.main.activity_decibel.*
import java.io.File
import java.lang.ref.WeakReference
import java.text.DecimalFormat

/**
 * 时间：2019/4/30 18:20
 * 创建：吕卡
 * 描述：分贝测试仪
 */

class DecibelActivity : BaseActivity() {

    private val mediaRecorder = MediaRecorder()
    private val mFile by lazy { File(externalCacheDir, "recorder.amr") }

    private val handler by lazy { MyHandler(this) }
    private var small = 100f
    private var big = 0f

    class MyHandler(activity: DecibelActivity) : Handler() {
        private val reference = WeakReference<DecibelActivity>(activity)
        override fun handleMessage(msg: Message?) {
            val activity = reference.get()
            activity?.updateViews()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("分贝测试仪")
        setContentView(R.layout.activity_decibel)
        initBaseViews()
        requestPermission()
    }

    private fun requestPermission() {
        PermissionRequester.requestPermission(this,
                mutableListOf(android.Manifest.permission.RECORD_AUDIO),
                {
                    createRecodeAudio()
                    startRecord()
                    view_wave.start()
                },
                { showToast("您拒绝了录音权限") })
    }

    private fun createRecodeAudio() {
        try {
            mediaRecorder.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(mFile.absolutePath)
                prepare()
                start()
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            stopRecord()
            showToast("录音初始化失败")
        }
    }

    private fun startRecord() {
        handler.sendEmptyMessageDelayed(0, 300)
    }

    private fun stopRecord() {
        mediaRecorder.reset()
        mediaRecorder.release()
    }

    private fun updateViews() {
        val amplitude = mediaRecorder.maxAmplitude.toDouble()
        val value = numAccuracy(Math.log10(amplitude).toFloat() * 20)
        startRecord()
        if (value == 0f) return
        current_value.text = "$value"
        if (value > big) {
            big = value
            tv_big.text = "当前最大分贝：$value DB"
        }
        if (value < small) {
            small = value
            tv_small.text = "当前最小分贝：$value DB"
        }
    }

    // 数据的精确度转换
    private fun numAccuracy(origin: Float): Float {
        return try {
            DecimalFormat("0.0").format(origin.toDouble()).toFloat()
        } catch (exception: Exception) {
            0f
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRecord()
        view_wave.stop()
        handler.removeCallbacksAndMessages(null)
    }
}
