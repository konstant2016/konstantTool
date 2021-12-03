package com.konstant.tool.lite.module.stock.vertical

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.stock.AdapterViewPager
import com.konstant.tool.lite.module.stock.StockViewModel
import com.konstant.tool.lite.module.stock.horizontal.StockHorizontalHistoryActivity
import kotlinx.android.synthetic.main.activity_stock_vertical_history.*
import kotlinx.android.synthetic.main.title_layout.*
import java.util.*

/**
 * 时间：6/8/21 11:54 AM
 * 作者：吕卡
 * 备注：股票历史的竖屏展示页面
 */

class StockVerticalHistoryActivity : BaseActivity() {

    private val mViewModel by lazy { ViewModelProvider(this).get(StockViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_vertical_history)
        initObservable()
    }

    private fun initObservable() {
        showLoading(true)
        setExtensionClickListener {
            startActivity(StockHorizontalHistoryActivity::class.java)
        }
        mViewModel.getStockMap().observe(this, Observer {
            showLoading(false)
            val fragmentList = mutableListOf<Fragment>()
            it.forEach {
                val value = it.value
                val data = value[0]
                val fragment = StockVerticalHistoryFragment.getInstance(data.year, data.month)
                fragmentList.add(fragment)
            }
            setUpViews(fragmentList)
            setUpCurrentMonth(it.keys)
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
    private fun setUpCurrentMonth(keys: Set<String>) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val index = keys.indexOfFirst { it == "$year-$month" }
        try {
            view_pager.setCurrentItem(index, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}