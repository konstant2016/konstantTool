package com.konstant.develop.dsl

import android.os.Bundle
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dsl.*

class DSLActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsl)
        initBaseViews()
    }

    private fun initBaseViews() {
        val fragmentList = listOf(DSLFragment(), TransferFragment(), FlexFragment())
        val titleList = listOf("JSON预览", "J2V8引擎", "弹性布局")
        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
}