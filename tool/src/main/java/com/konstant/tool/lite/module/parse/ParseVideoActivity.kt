package com.konstant.tool.lite.module.parse

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.BaseFragmentAdapter
import com.konstant.tool.lite.base.BasePagerChangeListener
import kotlinx.android.synthetic.main.activity_parse.*

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
        showTitleBar(false)
        layout_title.setOnClickListener { hideSoftKeyboard() }
        img_back_parse.setOnClickListener { finish() }
        val list = listOf(ListFragment.newInstance(),ParseFragment.newInstance())
        view_pager.adapter = BaseFragmentAdapter(supportFragmentManager, list)

        view_pager.addOnPageChangeListener(object : BasePagerChangeListener() {
            override fun onPageSelected(position: Int) {
                view_segment.setSelectedIndex(position)
            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                hideSoftKeyboard()
                super.onPageScrolled(p0, p1, p2)
            }
        })

        view_segment.setOnSegmentControlClickListener { view_pager.currentItem = it }

    }
}

