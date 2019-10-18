package com.konstant.tool.lite.module.date

import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragmentAdapter
import kotlinx.android.synthetic.main.activity_date_calculation.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2019/9/29 14:14
 * 创建：菜籽
 * 描述：日期计算
 */

class DateCalculationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_calculation)
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        setSegmentalTitle("日期推算", "日期间隔")
        val list = listOf(CalculationFragment.newInstance(), IntervalFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)
        view_segment.setUpWithViewPager(view_pager)
    }
}
