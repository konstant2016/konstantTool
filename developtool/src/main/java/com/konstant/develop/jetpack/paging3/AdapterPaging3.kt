package com.konstant.develop.jetpack.paging3

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import kotlinx.android.synthetic.main.layout_item_paging3.view.*

/**
 * 时间：2022/5/29 00:33
 * 作者：吕卡
 * 备注：Paging3的数据适配器，需要实现DiffUtil方法
 */

class AdapterPaging3 : PagingDataAdapter<ItemPaging3, AdapterPaging3.ViewHolder>(itemComparator) {

    companion object {

        val itemComparator = object : DiffUtil.ItemCallback<ItemPaging3>() {
            /**
             * 用来判断新旧条目是否一样，确定是否需要刷新
             * 只有当此方法返回true时，才会执行下面的方法
             * 如果此方法返回false，则下面的方法不会执行
             * 举个例子：当前item的布局没有发生变化，只是布局里面的数据发生了变化，
             *          比如字符串从AAA变成了BBB，则这里返回true，表示不需要重绘当前item的布局
             *
             */
            override fun areItemsTheSame(oldItem: ItemPaging3, newItem: ItemPaging3): Boolean {
                return true
            }

            /**
             * 用来确定是否是同一条目是否一样
             * 注意与上面的方法区分
             * 这里返回true时，只会刷新当前item布局中发生变化的那一部分UI，其余地方不需要动
             * 如果这里返回false，则当前item不会刷新，显示内容也不会发生变化
             */
            override fun areContentsTheSame(oldItem: ItemPaging3, newItem: ItemPaging3): Boolean {
                return !TextUtils.equals(oldItem.title, newItem.title)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_title.text = getItem(position)?.title
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_paging3, parent, false)
        return ViewHolder(view)
    }

}