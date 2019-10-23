package com.konstant.tool.lite.module.speed

import android.os.Bundle
import android.text.TextUtils
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.network.NetService
import kotlinx.android.synthetic.main.activity_net_speed.*
import java.text.DecimalFormat

/**
 * 时间：2019/5/5 18:36
 * 创建：吕卡
 * 描述：网速测试
 */

class NetSpeedActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_speed)
        setTitle("网速测试")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        btn_start.setOnClickListener {
            var max = 10 * 1024 * 1024L
            if (!TextUtils.isEmpty(et_input.text)) {
                val stamp = et_input.text.toString().toLong() * 1024 * 1024
                if (stamp >= 50 * 1024 * 1024) {
                    max = 50 * 1024 * 1024
                }
            }
            btn_start.isClickable = false
            val time = System.currentTimeMillis()
            tv_result.text = "测试中..."
            NetService.downloadSafe(max) { percent, _, status ->
                runOnUiThread {
                    view_progress.progress = ((percent * 100 / max).toInt())
                    tv_percent.text = "${percent * 100 / max}%"
                    if (percent >= max) {
                        tv_result.text = speedConvert(System.currentTimeMillis() - time, max)
                        btn_start.isClickable = true
                    }
                }
            }
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
