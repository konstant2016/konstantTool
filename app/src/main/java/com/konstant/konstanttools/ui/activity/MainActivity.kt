package com.konstant.konstanttools.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import com.konstant.konstanttools.test.ForegroundService
import com.konstant.konstanttools.ui.adapter.AdapterViewpager
import com.konstant.konstanttools.ui.fragment.LocalTestFragment
import com.konstant.konstanttools.ui.fragment.ToolsFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 描述:主界面
 * 创建人:菜籽
 * 创建时间:2017/12/28 下午6:15
 * 备注:
 */

class MainActivity : BaseActivity() {

    private val mAdapter by lazy { AdapterViewpager(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeBackLayout.setEnableGesture(false)
        initBaseViews()
//        startService()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        segment_main_activity.setOnSegmentControlClickListener { viewpager_main_activity.currentItem = it }

        val list = listOf(ToolsFragment(), LocalTestFragment())

        viewpager_main_activity.adapter = mAdapter

        mAdapter.updateFragmentList(list)

        viewpager_main_activity.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                segment_main_activity.setSelectedIndex(position)
            }
        })
    }

     fun startService() {
         Log.i(this.localClassName,"开启Service")
        val intentService = Intent(this, ForegroundService::class.java)
        startService(intentService)
    }
}