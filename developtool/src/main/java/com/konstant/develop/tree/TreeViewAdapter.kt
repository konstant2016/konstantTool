package com.konstant.develop.tree

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.konstant.develop.R

class TreeViewAdapter {

    private val dataList = mutableListOf<Response>()

    fun setDataList(list: List<Response>) {
        dataList.clear()
        dataList.addAll(list)
    }

    fun getDataList() = dataList

    fun onCreateView(context: Context, position: Int, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_item_tree_view, parent, false)
        view.setOnClickListener {
            Log.d("ZoomLayout", "onClick")
            Toast.makeText(context,"点击了",Toast.LENGTH_SHORT).show()
        }
        return view
    }

    fun getItemCount(): Int = dataList.size

    fun getItemPosition(position: Int): Response {
        return dataList[position]
    }
}