package com.konstant.tool.lite.module.busline.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.busline.BusLineItem
import com.amap.api.services.busline.BusStationItem
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.busline.adapter.AdapterBusStation
import kotlinx.android.synthetic.main.fragment_detail.*
import java.text.DecimalFormat

/**
 * 时间：2018/8/2 18:34
 * 作者：吕卡
 * 描述：
 */
class DetailFragment : BaseFragment() {

    private val mBusStationList = ArrayList<BusStationItem>()
    private val mAdapterStation by lazy { AdapterBusStation(mBusStationList) }

    companion object {
        fun newInstance() = DetailFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater!!.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_detail.apply {
            layoutManager = LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter = mAdapterStation
        }
    }

    fun setDetailData(item: BusLineItem) {
        mBusStationList.clear()
        mBusStationList.addAll(item.busStations)
        tv_name.text = "${item.busLineName}"
        tv_company.text = "${if (TextUtils.isEmpty(item.busCompany)) "暂无" else item.busCompany}"
        tv_price.text = "价格： ${item.basicPrice}元——${item.totalPrice}元"
        tv_distance.text = "全程： ${DecimalFormat("#.0").format(item.distance)}公里"
        mAdapterStation.notifyDataSetChanged()
        updateData()
    }

    private fun updateData(){
        layout_detail.visibility = if (mBusStationList.isEmpty()) View.GONE else View.VISIBLE
        tv_none.visibility = if (mBusStationList.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onFragmentResume() {
        updateData()
    }

}