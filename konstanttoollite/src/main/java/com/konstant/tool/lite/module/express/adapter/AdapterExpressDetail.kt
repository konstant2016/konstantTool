package com.konstant.tool.lite.module.express.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.express.server.ExpressResponse

/**
 * 描述:物流详情页面的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午2:28
 * 备注:
 */

class AdapterExpressDetail(val context: Context, val datas: List<ExpressResponse.DataBean>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_express_detail, parent, false)
        val time = view.findViewById(R.id.tv_time) as TextView
        val location = view.findViewById(R.id.tv_location) as TextView
        time.text = datas[position].ftime
        location.text = datas[position].context

        if (position == datas.size - 1) {
            view.findViewById(R.id.line_bottom).visibility = View.GONE
        }
        if (position == 0) {
            view.findViewById(R.id.line_top).visibility = View.GONE
        }
        return view
    }

    override fun getItem(position: Int): Any = datas[position]


    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = datas.size

}