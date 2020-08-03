package com.konstant.tool.lite.module.live

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import cn.jzvd.JZMediaSystem
import cn.jzvd.JzvdStd
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.network.response.TvLiveResponse
import kotlinx.android.synthetic.main.activity_tv_live.*

class TVLiveActivity : BaseActivity() {

    private var mTimeStamp = System.currentTimeMillis()
    private val mPresenter = TvLivePresenter(mDisposable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_live)
        setSwipeBackEnable(false)
        showTitleBar(false)
        showStatusBar(false)
        setDrawerLayoutStatus(false)
        getData()
    }

    private fun getData() {
        showLoading(true)
        mPresenter.getLiveList({
            showLoading(false)
            initViews(it)
        }, {
            showError(true)
        })
    }

    override fun onRetryClick() {
        getData()
    }

    private fun initViews(tvLiveList: List<TvLiveResponse.ResultsBean>) {
        JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT)
        video_player.apply {
            setPlayUrl(tvLiveList[0].channelUrl)
            setOnClickListener { showRecyclerView(view_list.visibility == View.GONE) }
        }
        val adapter = AdapterLive(tvLiveList)
        adapter.setOnItemClickListener { _, position -> setPlayUrl(tvLiveList[position].channelUrl) }
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
            showToast(getString(R.string.live_exit_toast))
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
