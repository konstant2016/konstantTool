package com.konstant.tool.lite.module.busline.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.busline.BusLineItem
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.busline.adapter.AdapterBusLine
import com.konstant.tool.lite.module.busline.param.BusLineResult
import kotlinx.android.synthetic.main.fragment_result.*
import org.greenrobot.eventbus.EventBus

/**
 * 时间：2018/8/2 18:37
 * 作者：吕卡
 * 描述：
 */
class ResultFragment : BaseFragment() {

    private val mBusLineList = ArrayList<BusLineItem>()
    private val mAdapterStation by lazy { AdapterBusLine(mActivity, mBusLineList) }

    companion object {
        fun newInstance() = ResultFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View =
            layoutInflater.inflate(R.layout.fragment_result, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_result.apply {
            adapter = mAdapterStation
            setOnItemClickListener { _, _, position, _ ->
                EventBus.getDefault().post(BusLineResult(2, mBusLineList[position]))
            }
        }
    }

    // 查询路线
    fun queryBusLine(route: String, city: String) {
        updateViewState(false, true, false)
        val query = BusLineQuery(route, BusLineQuery.SearchType.BY_LINE_NAME, city)
        query.apply {
            pageSize = 50
            pageNumber = 0
        }
        val search = BusLineSearch(mActivity, query)
        search.apply {
            setOnBusLineSearchListener { busLineResult, rCode ->
                if (rCode != 1000 || busLineResult.busLines.size == 0) {
                    updateViewState(false, false, true)
                    return@setOnBusLineSearchListener
                }
                mBusLineList.clear()
                mBusLineList.addAll(busLineResult.busLines)
                mAdapterStation.notifyDataSetChanged()
                updateViewState(true, false, false)
            }
            searchBusLineAsyn()
        }
    }

    // 刷新界面
    private fun updateViewState(result: Boolean, anim: Boolean, error: Boolean) {
        tv_none.visibility = View.GONE
        list_result.visibility = if (result) View.VISIBLE else View.GONE
        layout_anim.visibility = if (anim) View.VISIBLE else View.GONE
        layout_error.visibility = if (error) View.VISIBLE else View.GONE
    }
}