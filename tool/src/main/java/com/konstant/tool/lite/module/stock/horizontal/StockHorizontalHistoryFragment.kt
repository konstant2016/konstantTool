package com.konstant.tool.lite.module.stock.horizontal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_stock_history_horizontal.*

/**
 * 时间：2021/6/5 20:59
 * 作者：吕卡
 * 备注：每个月的股票情况，包含上下两个fragment，上面的日历形式，下面的是图标形式
 */

class StockHorizontalHistoryFragment : BaseFragment() {

    companion object {
        private const val KEY_YEAR = "YEAR"
        private const val KEY_MONTH = "MONTH"
        fun getInstance(year: Int, month: Int): Fragment {
            val bundle = Bundle()
            bundle.putInt(KEY_YEAR, year)
            bundle.putInt(KEY_MONTH, month)
            val fragment = StockHorizontalHistoryFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_history_horizontal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year = arguments?.getInt(KEY_YEAR) ?: -1
        val month = arguments?.getInt(KEY_MONTH) ?: -1
        val monthFragment = StockMonthFragment.getInstance(year, month)
        val chartFragment = StockChartHorizontalFragment.getInstance(year, month)
        val list = listOf(monthFragment, chartFragment)
        val adapter = AdapterVerticalViewPager(childFragmentManager, lifecycle, list)
        view_pager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        view_pager2.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val year = arguments?.getInt(KEY_YEAR) ?: -1
        val month = arguments?.getInt(KEY_MONTH) ?: -1
        val title = "$year - $month "
        setTitle(title)
    }
}