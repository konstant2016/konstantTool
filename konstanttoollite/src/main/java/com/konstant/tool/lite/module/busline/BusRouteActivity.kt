package com.konstant.tool.lite.module.busline

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.animation.AnimationUtils
import com.amap.api.services.busline.BusLineItem
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import com.amap.api.services.busline.BusStationItem
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.AreaManager
import com.konstant.tool.lite.view.KonstantArrayAdapter
import kotlinx.android.synthetic.main.activity_bus_route.*
import java.text.DecimalFormat


class BusRouteActivity : BaseActivity() {

    private val TAG = "BusRouteActivity"

    private val mBusStationList = ArrayList<BusStationItem>()
    private val mCountryNameList by lazy { AreaManager.getAreaList().map { it.name } }
    private val mBusLineList = ArrayList<BusLineItem>()

    private val mAdapterDetail by lazy { AdapterBusStation(mBusStationList) }
    private val mAdapterAutoCity by lazy { KonstantArrayAdapter(this, mCountryNameList) }
    private val mAdapterStation by lazy { AdapterBusLine(this, mBusLineList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_route)
        setTitle("公交线路查询")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        recycler_detail.apply {
            layoutManager = LinearLayoutManager(this@BusRouteActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapterDetail
        }

        auto_tv_city.apply {
            setAdapter(mAdapterAutoCity)
        }

        list_result.apply {
            adapter = mAdapterStation
            setOnItemClickListener { _, _, position, _ ->
                mBusStationList.clear()
                val item = mBusLineList[position]
                mBusStationList.addAll(item.busStations)
                tv_name.text = "${item.busLineName}"
                tv_company.text = "${if (TextUtils.isEmpty(item.busCompany)) "暂无" else item.busCompany}"
                tv_price.text = "价格： ${item.basicPrice}元——${item.totalPrice}元"
                tv_distance.text = "全程： ${DecimalFormat("#.0").format(item.distance)}公里"
                mAdapterStation.notifyDataSetChanged()
                layout_detail.startAnimation(AnimationUtils.loadAnimation(this@BusRouteActivity, R.anim.anim_right_to_left))
                updateViewState(false, true, false, false)
            }
        }

        img_city_close.setOnClickListener { auto_tv_city.setText("") }

        img_line_close.setOnClickListener { et_bus.setText("") }

        btn_search.setOnClickListener {
            if (TextUtils.isEmpty(et_bus.text)) {
                showToast("记得输入公交路线哦~")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(auto_tv_city.text)) {
                showToast("记得选择城市哦~")
                return@setOnClickListener
            }
            hideSoftKeyboard()
            updateViewState(false, false, true, false)
            queryRoute(et_bus.text.toString(), BusLineQuery.SearchType.BY_LINE_NAME, auto_tv_city.text.toString())
        }
    }

    // 开始查询
    private fun queryRoute(route: String, type: BusLineQuery.SearchType, code: String) {
        val query = BusLineQuery(route, type, code)
        query.apply {
            pageSize = 50
            pageNumber = 0
        }
        val search = BusLineSearch(this, query)
        search.apply {
            setOnBusLineSearchListener { busLineResult, rCode ->
                if (rCode != 1000 || busLineResult.busLines.size == 0) {
                    updateViewState(false, false, false, true)
                    return@setOnBusLineSearchListener
                }
                mBusLineList.clear()
                mBusLineList.addAll(busLineResult.busLines)
                mAdapterStation.notifyDataSetChanged()
                updateViewState(true, false, false, false)
            }
            searchBusLineAsyn()
        }
    }

    override fun onBackPressed() {
        if (layout_detail.visibility == View.VISIBLE) {
            layout_detail.startAnimation(AnimationUtils.loadAnimation(this@BusRouteActivity, R.anim.anim_left_to_right))
            updateViewState(true, false, false, false)
            return
        }
        super.onBackPressed()
    }

    private fun updateViewState(result: Boolean, detail: Boolean, anim: Boolean, error: Boolean) {
        runOnUiThread {
            list_result.visibility = if (result) View.VISIBLE else View.GONE
            layout_detail.visibility = if (detail) View.VISIBLE else View.GONE
            layout_anim.visibility = if (anim) View.VISIBLE else View.GONE
            layout_error.visibility = if (error) View.VISIBLE else View.GONE
        }
    }
}
