package com.konstant.tool.lite.module.stock.horizontal

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.stock.AdapterViewPager
import com.konstant.tool.lite.module.stock.StockViewModel
import com.konstant.tool.lite.view.StatusBarUtil
import kotlinx.android.synthetic.main.activity_stock_month.*
import kotlinx.android.synthetic.main.title_layout.*
import java.util.*

/**
 * 时间：2021/6/5 20:42
 * 作者：吕卡
 * 备注：股票的历史记录横屏页面
 */

class StockHorizontalHistoryActivity : BaseActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(StockViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setCutoutMode(this)
        setContentView(R.layout.activity_stock_month)
        initObservable()
    }

    private fun initObservable() {
        showLoading(true)
        mViewModel.getStockMap().observe(this, Observer {
            showLoading(false)
            val fragmentList = mutableListOf<Fragment>()
            it.forEach {
                // value为该月份中的所有数据，所有数据的年、月值都是一样的，因此取第一个即可
                val value = it.value
                val data = value[0]
                val fragment = StockHorizontalHistoryFragment.getInstance(data.year, data.month)
                fragmentList.add(fragment)
            }
            setUpViews(fragmentList)
            setUpCurrentMonth(it.keys,view_pager)
        })
    }

    private fun setUpViews(fragmentList: List<Fragment>) {
        showLoading(false)
        val fragmentAdapter = AdapterViewPager(supportFragmentManager, fragmentList)
        view_pager.adapter = fragmentAdapter
        title_indicator.setViewPager(view_pager)
    }

    /**
     * 跳到当前月份
     */
    private fun setUpCurrentMonth(keys: Set<String>,viewPager: ViewPager) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val index = keys.indexOfFirst { it == "$year-$month" }
        if (index in 0 until viewPager.childCount){
            viewPager.setCurrentItem(index,true)
        }
    }
}