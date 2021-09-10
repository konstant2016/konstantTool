package com.konstant.tool.ui.activity.cardSwipe.demo04

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.R

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_horizontal,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = 80


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

}