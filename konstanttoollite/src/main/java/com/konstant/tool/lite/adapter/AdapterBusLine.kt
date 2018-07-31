package com.konstant.tool.lite.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.amap.api.services.busline.BusLineItem
import com.konstant.tool.lite.R

/**
 * 时间：2018/7/31 14:02
 * 作者：吕卡
 * 描述：公交路线的查询结果的适配器
 */
class AdapterBusLine(context: Context, val list: ArrayList<BusLineItem>) : BaseAdapter() {

    private val mLayoutInflate = LayoutInflater.from(context)

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
                ?: mLayoutInflate.inflate(R.layout.item_list_bus_result, parent, false)
        val holder = (view.tag ?: Holder(
                view.findViewById(R.id.tv_line_name) as TextView,
                view.findViewById(R.id.tv_line_company) as TextView
        )) as Holder
        setData(holder, list[position])
        view.tag = holder
        return view
    }

    private fun setData(holder: Holder, data: BusLineItem) {
        holder.name.text = "${data.busLineName}"
        holder.company.text = "${if (TextUtils.isEmpty(data.busCompany)) "暂无" else data.busCompany}"
    }

    class Holder(val name: TextView, val company: TextView)

}