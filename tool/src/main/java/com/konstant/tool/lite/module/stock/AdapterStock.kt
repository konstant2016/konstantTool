package com.konstant.tool.lite.module.stock

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseRecyclerAdapter
import com.konstant.tool.lite.data.bean.stock.StockData
import kotlinx.android.synthetic.main.item_stock_common.view.*
import kotlinx.android.synthetic.main.item_stock_total.view.*

class AdapterStock(private val stockList: List<StockData>) : BaseRecyclerAdapter<RecyclerView.ViewHolder>() {

    private val TYPE_COMMON = 0x01
    private val TYPE_BOTTOM = 0x02

    class StockViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class TotalViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_COMMON) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_common, parent, false)
            StockViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_total, parent, false)
            TotalViewHolder(view)
        }
    }

    override fun getItemCount() = stockList.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == stockList.size) return TYPE_BOTTOM
        return TYPE_COMMON
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        if (holder is StockViewHolder) {
            val stock = stockList[position]
            onBindItem(holder, stock)
        }
        if (holder is TotalViewHolder) {
            onBindTotal(holder)
        }
    }

    private fun onBindItem(holder: StockViewHolder, stock: StockData) {
        holder.itemView.apply {
            tv_name.text = "${stock.name}\n${stock.number.replace("sh","").replace("sz","")}"
            tv_price.text = double2String(stock.price)
            tv_count.text = String.format("%.1f", stock.count)
            tv_total.text = double2String(stock.count * stock.price)
            if (stock.isIncrease) {
                tv_name.setTextColor(Color.RED)
                tv_price.setTextColor(Color.RED)
            } else {
                tv_name.setTextColor(Color.GREEN)
                tv_price.setTextColor(Color.GREEN)
            }
        }
    }

    private fun onBindTotal(holder: TotalViewHolder) {
        var all = 0.0
        stockList.forEach {
            all += it.count * it.price
        }
        holder.itemView.tv_all.text = "当前持仓总计市值：${double2String(all)} 元"
    }

    private fun double2String(double: Double): String {
        return String.format("%.2f", double)
    }

}