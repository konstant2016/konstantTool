package com.konstant.tool.lite.module.express.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.module.express.server.ExpressData
import com.konstant.tool.lite.module.express.server.ExpressResponse
import kotlinx.android.synthetic.main.item_express_detail.view.*

/**
 * 描述:物流详情页面的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午2:28
 * 备注:
 */

class AdapterExpressDetail(val context: Context, val datas: List<ExpressData.Message>) : BaseRecyclerAdapter<AdapterExpressDetail.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_express_detail, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = datas.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = datas[position]
        holder.itemView.apply {
            tv_time.text = data.time
            tv_location.text = data.context
            line_top.visibility = if (position == 0) View.GONE else View.VISIBLE
            line_bottom.visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE
        }
    }

    class Holder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}