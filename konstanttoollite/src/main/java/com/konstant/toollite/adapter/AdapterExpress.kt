package com.konstant.toollite.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.activity.ExpressDetailActivity
import com.konstant.toollite.server.other.ExpressData
import com.konstant.toollite.util.CircleTransform
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import com.squareup.picasso.Picasso

/**
 * Created by konstant on 2018/4/4.
 */
class AdapterExpress(val context: Context, val expresses: ArrayList<ExpressData>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_express_list, parent, false)

        val img = view.findViewById<ImageView>(R.id.img_company)
        val remark = view.findViewById<TextView>(R.id.tv_remark)
        val company = view.findViewById<TextView>(R.id.tv_express_num)
        val state = view.findViewById<TextView>(R.id.tv_state)

        when (expresses[position].company) {
            "shunfeng" -> {
                Picasso.with(context).load(R.drawable.pic_shunfeng).transform(CircleTransform()).into(img)
                company.text = "顺丰物流:" + expresses[position].orderNo
            }
            "ems" -> {
                Picasso.with(context).load(R.drawable.pic_ems).transform(CircleTransform()).into(img)
                company.text = "中国邮政:" + expresses[position].orderNo
            }
            "zhongtong" -> {
                Picasso.with(context).load(R.drawable.pic_zhongtong).transform(CircleTransform()).into(img)
                company.text = "中通物流:" + expresses[position].orderNo
            }
            "yuantong" -> {
                Picasso.with(context).load(R.drawable.pic_yuantong).transform(CircleTransform()).into(img)
                company.text = "圆通物流:" + expresses[position].orderNo
            }
            "shentong" -> {
                Picasso.with(context).load(R.drawable.pic_shentong).transform(CircleTransform()).into(img)
                company.text = "申通物流:" + expresses[position].orderNo
            }
            "yunda" -> {
                Picasso.with(context).load(R.drawable.pic_yunda).transform(CircleTransform()).into(img)
                company.text = "韵达物流:" + expresses[position].orderNo
            }
            "tiantian" -> {
                Picasso.with(context).load(R.drawable.pic_tiantian).transform(CircleTransform()).into(img)
                company.text = "天天快递:" + expresses[position].orderNo
            }
        }

        state.text = expresses[position].state

        remark.text = expresses[position].remark

        return view

    }

    override fun getItem(position: Int): Any = expresses[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = expresses.size

}