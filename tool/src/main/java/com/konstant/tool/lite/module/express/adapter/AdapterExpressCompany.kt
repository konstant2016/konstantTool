package com.konstant.tool.lite.module.express.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.item_spinner_pull_down_bg.view.*

/**
 * 时间：2019/4/24 11:18
 * 创建：吕卡
 * 描述：选择物流公司的适配器
 */

class AdapterExpressCompany(val companyNamelist: Array<String>, val companyIdList: Array<String>) : RecyclerView.Adapter<AdapterExpressCompany.Holder>() {

    private var onClick: ((companyId: String, companyName: String) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): Holder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_spinner_pull_down_bg, viewGroup, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.text_label.text = companyNamelist[position]
        holder.itemView.setOnClickListener { onClick?.invoke(companyIdList[position], companyNamelist[position]) }
    }

    fun setOnItemClickListener(callback: (companyId: String, companyName: String) -> Unit) {
        onClick = callback
    }

    override fun getItemCount() = companyNamelist.size

    class Holder(view: View) : RecyclerView.ViewHolder(view)


}