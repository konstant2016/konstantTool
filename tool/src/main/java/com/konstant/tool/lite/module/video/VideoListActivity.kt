package com.konstant.tool.lite.module.video

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.activity_video_list.*

/**
 * 时间：2019/1/31 11:14
 * 创建：吕卡
 * 描述：视频列表
 */

class VideoListActivity : BaseActivity() {

    private val mVideoList = ArrayList<Video>()
    private val mAdapter = AdapterVideoList(mVideoList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)
        initBaseViews()
        requestPermission()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setTitle("视频播放器")
        mAdapter.setOnItemClickListener { _, position ->
            val intent = Intent(this, PlayActivity::class.java)
            intent.putExtra("path", mVideoList[position].path)
            startActivity(intent)
        }

        with(layout_recycler) {
            layoutManager = LinearLayoutManager(this@VideoListActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
    }

    private fun readVideoList() {
        showLoading(state = true)

        VideoScanner.scanVideo(VideoScanner.Type.MEDIA, {
            mVideoList.clear()
            mVideoList.addAll(it)
            mAdapter.notifyDataSetChanged()
            showLoading(state = false)
        }, { index, total ->
            showLoading(true, "已扫描: $index/$total")
        })
    }

    private fun requestPermission() {
        AndPermission.with(this)
                .permission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .onGranted {
                    readVideoList()
                }
                .onDenied { showToast("您拒绝了外置存储卡权限") }
                .start()
    }
}
