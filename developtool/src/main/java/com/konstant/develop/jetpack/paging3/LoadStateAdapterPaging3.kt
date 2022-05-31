package com.konstant.develop.jetpack.paging3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import kotlinx.android.synthetic.main.load_state_item_paging3.view.*

/**
 * 时间：2022/5/28 23:32
 * 作者：吕卡
 * 备注：网络状态的适配器
 */

class LoadStateAdapterPaging3 : LoadStateAdapter<LoadStateAdapterPaging3.LoadStateViewHolder>() {

    class LoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var listener: (() -> Unit)? = null

    fun setOnReloadClickListener(listener: () -> Unit) {
        this.listener = listener
    }

    /**
     * loadState 有三种状态
     *
     */
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        Log.d("ItemDataSource", "loadState:$loadState")
        holder.itemView.progress_bar.visibility = View.VISIBLE
        holder.itemView.tv_state.text = "正在加载中..."
        if (loadState is LoadState.NotLoading) {
            if (loadState.endOfPaginationReached) {
                holder.itemView.progress_bar.visibility = View.GONE
                holder.itemView.tv_state.text = "数据加载完毕"
            } else {
                holder.itemView.tv_state.text = "滑到头了，继续加载"
            }
            return
        }
        if (loadState is LoadState.Error) {
            holder.itemView.tv_state.text = "加载失败，点击重试"
            holder.itemView.progress_bar.visibility = View.GONE
            holder.itemView.setOnClickListener {
                listener?.invoke()
            }
            return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_item_paging3, parent, false)
        return LoadStateViewHolder(view)
    }

    /**
     * 是否显示当前的加载状态
     * 默认的是只有 loading中，loading失败时才会显示加载状态
     * 我们可以改掉super的代码，添加一条当加载完成时，也显示加载状态
     */
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        //return super.displayLoadStateAsItem(loadState)
        return loadState is LoadState.Loading || loadState is LoadState.Error || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }

}