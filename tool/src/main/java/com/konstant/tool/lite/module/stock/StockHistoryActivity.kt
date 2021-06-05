package com.konstant.tool.lite.module.stock

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import kotlinx.android.synthetic.main.activity_stock_month.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 时间：2021/6/5 20:42
 * 作者：吕卡
 * 备注：股票的历史记录页面
 */

class StockHistoryActivity : BaseActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(StockViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 28) {
            val params = window.attributes
            params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = params
        }

        setContentView(R.layout.activity_stock_month)
        initObservable()
    }

    private fun initObservable(){
        showLoading(true)
        mViewModel.getStockMap().observe(this, Observer {
            showLoading(false)
            val fragmentList = mutableListOf<Fragment>()
            it.forEach {
                val value = it.value
                val data = value[0]
                val fragment = StockHistoryFragment.getInstance(data.year, data.month)
                fragmentList.add(fragment)
            }
            setUpViews(fragmentList)
        })
    }

    private fun setUpViews(fragmentList: List<Fragment>) {
        showLoading(false)
        val fragmentAdapter = AdapterViewPager(supportFragmentManager, fragmentList)
        view_pager.adapter = fragmentAdapter
        title_indicator.setViewPager(view_pager)
    }
}