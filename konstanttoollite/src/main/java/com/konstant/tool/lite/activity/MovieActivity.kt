package com.konstant.tool.lite.activity

import android.os.Bundle
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.R
import com.konstant.tool.lite.adapter.AdapterMovieList
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.server.Service
import com.konstant.tool.lite.server.response.MovieListResponse
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import kotlinx.android.synthetic.main.activity_movie.*

class MovieActivity : BaseActivity() {

    var mPage = 1
    var isLoadMore = false

    private val mMovieList = ArrayList<MovieListResponse.Data.Movie>()
    private val mAdapter by lazy { AdapterMovieList(this, mMovieList) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        initBaseViews()
        setTitle("电影预告片")
        layout_refresh.startRefresh()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        grid_movie.adapter = mAdapter

        layout_refresh.setHeaderView(BezierLayout(this))
        layout_refresh.setEnableLoadmore(true)
        layout_refresh.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                isLoadMore = false
                mPage = 1
                requestData()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                isLoadMore = true
                mPage += 1
                requestData()
            }
        })
    }

    // 请求数据
    private fun requestData() {
        Service.queryMovieList(this, mPage) { state, data ->
            stopRefreshing()
            if (state) {
                val response = JSON.parseObject(data, MovieListResponse::class.java)
                updateUI(response)
            }
        }
    }

    // 停止刷新
    private fun stopRefreshing(){
        runOnUiThread {
            if (!isLoadMore) {
                layout_refresh.finishRefreshing()
            }else{
                layout_refresh.finishLoadmore()
            }
        }
    }

    // 刷新UI
    private fun updateUI(response: MovieListResponse) {
        if (response.code != 200) return
        runOnUiThread {
            if (!isLoadMore) {
                mMovieList.clear()
            }
            mMovieList.addAll(response.data.list)
            mAdapter.notifyDataSetChanged()
        }
    }
}
