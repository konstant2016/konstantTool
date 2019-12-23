package com.konstant.tool.lite.module.speed

import android.os.Bundle
import android.text.TextUtils
import android.widget.SeekBar
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.network.config.FileDownloader
import kotlinx.android.synthetic.main.activity_net_speed.*
import kotlinx.android.synthetic.main.activity_net_speed.btn_start
import kotlinx.android.synthetic.main.activity_net_speed.view_seekbar
import kotlinx.android.synthetic.main.activity_roll_text.*
import java.text.DecimalFormat
import kotlin.math.ceil

/**
 * 时间：2019/5/5 18:36
 * 创建：吕卡
 * 描述：网速测试
 */

class NetSpeedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_speed)
        setTitle(getString(R.string.speed_title))
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        view_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                current_value.text = "${getString(R.string.speed_input_hint_01)} ${progress + 5} ${getString(R.string.speed_input_hint_02)}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        btn_start.setOnClickListener {
            val value = (view_seekbar.progress + 5) * 1024 * 1024L
            btn_start.isClickable = false
            val time = System.currentTimeMillis()
            tv_result.text = getString(R.string.speed_testing)

            NetworkHelper.getSpeed(value, object : FileDownloader.DownloadListener {
                override fun onProgress(current: Long, total: Long) {
                    view_progress.setCurrent(ceil((current * 100 / value).toDouble()).toInt())
                    tv_percent.text = "${current * 100 / value}%"
                    if (current >= value) {
                        tv_result.text = speedConvert(System.currentTimeMillis() - time, value)
                        btn_start.isClickable = true
                    }
                }
            })
        }
    }

    private fun speedConvert(time: Long, size: Long): String {
        val s = size.toFloat() * 1000 / time
        if (s > 1024 * 1024 * 1024) {// GB
            return "${numAccuracy(s / 1024 / 1024 / 1024)} GB/s"
        }
        if (s > 1024 * 1024) {// MB
            return "${numAccuracy(s / 1024 / 1024)} MB/s"
        }
        return "${numAccuracy(s / 1024)} KB/s"
    }

    // 数据的精确度转换
    private fun numAccuracy(origin: Float): Float {
        return try {
            DecimalFormat("0.0").format(origin.toDouble()).toFloat()
        } catch (exception: Exception) {
            0f
        }
    }
}
