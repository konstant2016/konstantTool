package com.konstant.dsl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.konstant.dsl.flex.FlexFragment
import com.konstant.dsl.transfer.TransferFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBaseViews()
    }

    private fun initBaseViews() {
        val fragmentList = listOf(TransferFragment(), FlexFragment())
        val titleList = listOf("J2V8引擎", "弹性布局")
        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList, titleList)
        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
}