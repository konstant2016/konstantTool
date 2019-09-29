package com.konstant.tool.lite.module.parse

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragmentAdapter
import com.konstant.tool.lite.base.BasePagerChangeListener
import kotlinx.android.synthetic.main.activity_parse.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/8/1 17:34
 * 创建：菜籽
 * 描述：VIP视频解析
 */

class ParseVideoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parse)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setSegmentalTitle("视频解析", "地址解析")
        val list = listOf(ListFragment.newInstance(), ParseFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : BasePagerChangeListener() {
            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                hideSoftKeyboard()
            }
        })
    }
}