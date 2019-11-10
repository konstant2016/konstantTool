package com.konstant.tool.lite.module.date

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.activity_viewpager.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/9/29 14:14
 * 创建：菜籽
 * 描述：日期计算
 */

class DateCalculationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setSegmentalTitle(getString(R.string.date_title), getString(R.string.date_date_interval))
        val list = listOf(CalculationFragment.newInstance(), IntervalFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)
    }
}
