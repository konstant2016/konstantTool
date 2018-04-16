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

class KonstantArrayAdapter(context: Context, val resource: Int, val array: Array<String>) :
        ArrayAdapter<String>(context, resource, array) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val root = LayoutInflater.from(context).inflate(R.layout.item_spinner_pull_down_bg, parent, false)
        val tv = root.findViewById(R.id.text_label) as TextView
        tv.text = array[position]
        return root
    }

}