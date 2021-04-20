package com.konstant.tool.lite.module.stock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.data.bean.stock.StockData
import kotlinx.android.synthetic.main.item_stock.view.*

class AdapterStock(private val stockList: List<StockData>) : RecyclerView.Adapter<AdapterStock.StockViewHolder>() {

    class StockViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun getItemCount() = stockList.size

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockList[position]
        holder.itemView.apply {
            tv_name.text = stock.name
            tv_price.text = stock.price.toString()
            tv_count.text = stock.count.toString()
            tv_total.text = (stock.count * stock.price).toString()
        }
    }

}