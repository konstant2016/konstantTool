package com.konstant.tool.lite.module.parse

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragmentAdapter
import com.konstant.tool.lite.base.BasePagerChangeListener
import kotlinx.android.synthetic.main.activity_viewpager.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/8/1 17:34
 * 创建：菜籽
 * 描述：VIP视频解析
 */

class ParseVideoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initViews()
    }

    private fun initViews() {
        setSegmentalTitle(getString(R.string.parse_video), getString(R.string.parse_address))
        val list = listOf(ListFragment.newInstance(), ParseFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : BasePagerChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                hideSoftKeyboard()
            }
        })
    }
}