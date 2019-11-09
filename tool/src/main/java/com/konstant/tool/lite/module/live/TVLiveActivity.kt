package com.konstant.tool.lite.module.live

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jzvd.JzvdStd
import com.google.gson.Gson
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.bean.live.LiveData
import kotlinx.android.synthetic.main.activity_tv_live.*

class TVLiveActivity : BaseActivity() {

    private val url = "http://223.110.242.130:6610/cntv/live1/cctv-1/1.m3u8"
    private var mTimeStamp = System.currentTimeMillis()

    private val mLiveData by lazy {
        val txt = resources.assets.open("LiveSource.json").bufferedReader().readText()
        Gson().fromJson(txt, LiveData::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_live)
        initBaseViews()
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
        }
        val adapter = AdapterLive(mLiveData.channel)
        adapter.setOnItemClickListener { _, position -> setPlayUrl(mLiveData.address[position]) }
        view_list.apply {
            layoutManager = LinearLayoutManager(this@TVLiveActivity, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
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

    override fun onBackPressed() {
        if (view_list.visibility == View.VISIBLE) {
            showRecyclerView(false)
            return
        }
        if (System.currentTimeMillis() - mTimeStamp > 2000) {
            showToast("再按一次退出播放")
            mTimeStamp = System.currentTimeMillis()
            return
        }
        super.onBackPressed()
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
        cancelToast()
        super.onDestroy()
    }
}
