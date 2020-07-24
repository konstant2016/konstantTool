package com.konstant.tool.lite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.konstant.tool.lite.R

/**
 * 描述:
 * 创建人:菜籽
 * 创建时间:2018/4/4 下午11:58
 * 备注:
 */

class KonstantArrayAdapter(context: Context, val array: List<String>) :
        ArrayAdapter<String>(context, R.layout.item_spinner_bg, array) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val root = LayoutInflater.from(context).inflate(R.layout.item_pop_dialog, parent, false)
        val tv = root.findViewById(R.id.tv_item) as TextView
        tv.text = array[position]
        return root
    }

}