package com.konstant.tool.lite.module.live

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AnimationUtils
import cn.jzvd.JzvdStd
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tv_live.*

class TVLiveActivity : BaseActivity() {

    val url = "http://223.110.242.130:6610/cntv/live1/cctv-1/1.m3u8";

    private val mChannelList = ArrayList<String>()
    private val mMap by lazy {
        val txt = resources.assets.open("liveSource.json").bufferedReader().readText()
        JSON.parseObject(txt, HashMap::class.java)
    }
    private val mAdapter = AdapterLive(mChannelList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_live)
        initBaseViews()
        initData()
    }

    private fun initData() {
        mMap.forEach { mChannelList.add(it.key.toString()) }
        mAdapter.notifyDataSetChanged()
    }

    override fun initBaseViews() {
        setSwipeBackEnable(false)
        showTitleBar(false)
        showStatusBar(false)
        setDrawerLayoutStatus(false)
        JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);
        video_player.apply {
            setPlayUrl(url)
            setOnClickListener { showRecyclerView(view_list.visibility == View.GONE) }
            mAdapter.setOnItemClickListener { _, position -> setPlayUrl(mMap[mChannelList[position]].toString()) }
            view_list.apply {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@TVLiveActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
                adapter = mAdapter
            }
        }
    }

    private fun showRecyclerView(show: Boolean) {
        showStatusBar(show)
        if (show) {
            view_list.visibility = View.VISIBLE
            view_list.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_left_to_right))
        } else {
            view_list.visibility = View.GONE
            view_list.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_right_to_left))
        }
    }

    private fun setPlayUrl(url: String?) {
        url?.apply {
            showRecyclerView(false)
            video_player.apply {
                reset(); setUp(url, "");setScreenFullscreen();startVideo()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        JzvdStd.goOnPlayOnResume()
    }

    override fun onStop() {
        JzvdStd.goOnPlayOnPause()
        super.onStop()
    }

    override fun onDestroy() {
        JzvdStd.releaseAllVideos()
        super.onDestroy()
    }
}
