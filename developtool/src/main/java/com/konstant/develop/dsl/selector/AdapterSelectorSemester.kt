package com.konstant.develop.dsl.selector

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.develop.R
import kotlinx.android.synthetic.main.item_selector_semester.view.*

/**
 * 时间：2022/3/18 6:25 下午
 * 作者：吕卡
 * 备注：册别选择器
 */

class AdapterSelectorSemester : RecyclerView.Adapter<SemesterViewHolder>() {

    private val mSemesterList = mutableListOf<Semester>()
    private var mOnItemClickListener: ((position: Int) -> Unit)? = null

    fun setData(semesterList: List<Semester>){
        mSemesterList.clear()
        mSemesterList.addAll(semesterList)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        mOnItemClickListener = listener
    }

    private fun onItemClicked(view: View, position: Int) {
        val index = mSemesterList.indexOfFirst { it.selected }
        if (index > -1) {
            mSemesterList[index].selected = false
            updatePosition(view, index)
        }
        mSemesterList[position].selected = true
        updatePosition(view, position)
        mOnItemClickListener?.invoke(position)
    }

    private fun updatePosition(view: View, position: Int) {
        view.post {
            if (position in 0 .. this.itemCount){
                notifyItemChanged(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SemesterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_selector_semester, parent, false)
        return SemesterViewHolder(view)
    }

    override fun onBindViewHolder(holder: SemesterViewHolder, position: Int) {
        val name = holder.itemView.semester_name
        val data = mSemesterList[position]
        name.text = data.name
        if (data.selected) {
            name.setTextColor(Color.parseColor("#518AFF"))
        } else {
            name.setTextColor(Color.parseColor("#222222"))
        }
        holder.itemView.setOnClickListener {
            onItemClicked(it,position)
        }
    }

    override fun getItemCount() = mSemesterList.size
}

class SemesterViewHolder(view: View) : RecyclerView.ViewHolder(view)