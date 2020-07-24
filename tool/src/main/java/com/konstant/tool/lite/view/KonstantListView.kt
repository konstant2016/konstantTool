package com.konstant.tool.lite.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.konstant.tool.lite.R
import kotlinx.android.synthetic.main.item_pop_dialog.view.*

class KonstantListView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : ListView(context, attributeSet, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val expandSpec = MeasureSpec.makeMeasureSpec(Int.MAX_VALUE.shr(2), MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}

class Adapter(val context: Context, val list: List<String>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_pop_dialog, parent, false)
        view.tv_item.text = list[position]
        return view
    }

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size

}