package com.konstant.tool.lite.module.stock.vertical

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BasePagerChangeListener
import com.konstant.tool.lite.module.stock.AdapterViewPager
import com.konstant.tool.lite.module.stock.StockManager
import com.konstant.tool.lite.module.stock.StockViewModel
import com.konstant.tool.lite.module.stock.horizontal.StockHorizontalHistoryActivity
import kotlinx.android.synthetic.main.activity_stock_month.*
import kotlinx.android.synthetic.main.activity_stock_vertical_history.*
import kotlinx.android.synthetic.main.activity_stock_vertical_history.view_pager
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

    override fun onResume() {
        super.onResume()
        val currentDate = StockManager.getCurrentDate()
        if (TextUtils.isEmpty(currentDate)) return
        val keys = mViewModel.getStockMap().value?.keys ?: return
        setUpCurrentMonth(currentDate, keys)
    }

    private fun initObservable() {
        val fragmentList = mutableListOf<StockVerticalHistoryFragment>()
        showLoading(true)
        setExtensionClickListener {
            try {
                val fragment = fragmentList[view_pager.currentItem]
                val currentDate = fragment.getCurrentDate()
                StockManager.setCurrentDate(currentDate)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            startActivity(StockHorizontalHistoryActivity::class.java)
        }
        mViewModel.getStockMap().observe(this) {
            showLoading(false)
            it.forEach {
                val value = it.value
                val data = value[0]
                val fragment = StockVerticalHistoryFragment.getInstance(data.year, data.month)
                fragmentList.add(fragment)
            }
            setUpViews(fragmentList)
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            setUpCurrentMonth("$year-$month", it.keys)
        }
    }

    private fun setUpViews(fragmentList: List<Fragment>) {
        showLoading(false)
        val fragmentAdapter = AdapterViewPager(supportFragmentManager, fragmentList)
        view_pager.adapter = fragmentAdapter
        if (fragmentList.size < 15){
            title_indicator.setViewPager(view_pager)
            return
        }
        view_pager.addOnPageChangeListener(object :BasePagerChangeListener(){
            override fun onPageSelected(position: Int) {
                val total = fragmentList.size
                val current = view_pager.currentItem + 1
                setSubTitle("$current / $total")
            }
        })
    }

    /**
     * 跳到指定月份月份
     */
    private fun setUpCurrentMonth(date: String, keys: Set<String>) {
        try {
            val index = keys.indexOfFirst { it == date }
            view_pager.setCurrentItem(index, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}