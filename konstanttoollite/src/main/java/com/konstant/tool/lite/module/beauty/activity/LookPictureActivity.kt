package com.konstant.tool.lite.module.beauty

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import com.konstant.tool.lite.base.BaseActivity
import java.util.*

/**
 * 描述:看图的详情页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class LookPictureActivity : BaseActivity() {

    private lateinit var mAdapter: AdapterLookPicture
    private val mUrlList = ArrayList<String>()

    private lateinit var mViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        mViewPager = ViewPager(this)
                .apply {
                    layoutParams = ViewGroup.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.MATCH_PARENT)
                    setBackgroundColor(Color.BLACK)
                }
        setContentView(mViewPager)

        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
    }

    override fun initBaseViews() {

        val intent = intent
        mUrlList.addAll(intent.getStringArrayListExtra("urlList"))
        Log.i("连接集合", mUrlList.toString())

        mAdapter = AdapterLookPicture(this, mUrlList)
        mViewPager.adapter = mAdapter

        val index = intent.getIntExtra("index", 0)
        mViewPager.currentItem = index

    }

}
