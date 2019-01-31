package com.konstant.tool.lite.module.video

import android.os.Bundle
import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_play.*

/**
 * 时间：2019/1/31 19:22
 * 创建：吕卡
 * 描述：视频播放页面
 */
class PlayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        hideTitleBar()

        val path = intent?.getStringExtra("path")

        with(layout_video) {
            setUp(path, "", Jzvd.SCREEN_WINDOW_NORMAL)
            startVideo()
        }

    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }
}
