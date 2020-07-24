package com.konstant.tool.lite.module.skip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_skip_advance.view.*

/**
 * 描述：自动跳过高级设置的适配器
 * 创建者：吕卡
 * 时间：2020/7/24:5:51 PM
 */
class AdapterAdvanceSkip(private val rulesList: List<AutoSkipManager.CustomRule>) : BaseRecyclerAdapter<AdapterAdvanceSkip.SkipHolder>() {

    class SkipHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkipHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skip_advance, parent, false)
        return SkipHolder(view)
    }

    override fun getItemCount(): Int {
        return rulesList.size
    }

    override fun onBindViewHolder(holder: SkipHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val customRule = rulesList[position]
        holder.itemView.tv_package_name.text = "包名：${customRule.packageName}"
        holder.itemView.tv_class_name.text = "类名：${customRule.className}"
        holder.itemView.tv_resource_name.text = "资源名：${customRule.resourceId}"
    }
}