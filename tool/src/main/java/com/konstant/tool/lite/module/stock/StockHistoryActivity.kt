package com.konstant.tool.lite.module.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.bean.stock.StockHistory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_stock_month.*
import kotlinx.android.synthetic.main.title_layout.*
import java.util.*

class StockHistoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_month)
        initBaseViews()
    }

    private fun initBaseViews() {
        val dispose = Observable.just(readData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    setUpViews(it)
                }
        mDisposable.add(dispose)
    }

    private fun readData(): List<Fragment> {
        showLoading(true)
        val stockList = StockManager.getStockHistory()
        Collections.sort(stockList)
        val map = mutableMapOf<String, MutableList<StockHistory>>()
        stockList.forEach {
            val key = "${it.year}-${it.month}"
            if (map.contains(key)) {
                val monthList = map[key]
                monthList?.add(it)
            } else {
                val monthList = mutableListOf<StockHistory>()
                monthList.add(it)
                map[key] = monthList
            }
        }
        val fragmentList = mutableListOf<Fragment>()
        map.forEach {
            val value = it.value
            val fragment = StockHistoryFragment()
            val data = value[0]
            fragment.setData(data.year, data.month, value)
            fragmentList.add(fragment)
        }
        return fragmentList
    }

    private fun setUpViews(fragmentList: List<Fragment>) {
        showLoading(false)
        val fragmentAdapter = AdapterViewPager(supportFragmentManager, fragmentList)
        view_pager.adapter = fragmentAdapter
        title_indicator.setViewPager(view_pager)
    }
}