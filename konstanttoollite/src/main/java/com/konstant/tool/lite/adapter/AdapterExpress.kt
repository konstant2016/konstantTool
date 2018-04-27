package com.konstant.tool.lite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.data.ExpressData
import com.konstant.tool.lite.util.CircleTransform
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

/**
 * 描述:快递列表的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:08
 * 备注:
 */

class AdapterExpress(val context: Context, val expresses: ArrayList<ExpressData>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_express_list, parent, false)

        val img = view.findViewById(R.id.img_company) as ImageView
        val remark = view.findViewById(R.id.tv_remark) as TextView
        val company = view.findViewById(R.id.tv_express_num) as TextView
        val state = view.findViewById(R.id.tv_state) as TextView

        when (expresses[position].company) {
            "shunfeng" -> {
                Picasso.with(context).load(R.drawable.pic_shunfeng).transform(CircleTransform()).into(img)
                company.text = "顺丰速运:" + expresses[position].orderNo
            }
            "ems" -> {
                Picasso.with(context).load(R.drawable.pic_ems).transform(CircleTransform()).into(img)
                company.text = "中国邮政:" + expresses[position].orderNo
            }
            "zhongtong" -> {
                Picasso.with(context).load(R.drawable.pic_zhongtong).transform(CircleTransform()).into(img)
                company.text = "中通快递:" + expresses[position].orderNo
            }
            "yuantong" -> {
                Picasso.with(context).load(R.drawable.pic_yuantong).transform(CircleTransform()).into(img)
                company.text = "圆通快递:" + expresses[position].orderNo
            }
            "shentong" -> {
                Picasso.with(context).load(R.drawable.pic_shentong).transform(CircleTransform()).into(img)
                company.text = "申通快递:" + expresses[position].orderNo
            }
            "yunda" -> {
                Picasso.with(context).load(R.drawable.pic_yunda).transform(CircleTransform()).into(img)
                company.text = "韵达快递:" + expresses[position].orderNo
            }
            "tiantian" -> {
                Picasso.with(context).load(R.drawable.pic_tiantian).transform(CircleTransform()).into(img)
                company.text = "天天快递:" + expresses[position].orderNo
            }
            "huitongkuaidi" -> {
                Picasso.with(context).load(R.drawable.pic_huiotng).transform(CircleTransform()).into(img)
                company.text = "汇通:快递" + expresses[position].orderNo
            }
            "quanfengkuaidi" -> {
                Picasso.with(context).load(R.drawable.pic_quanfeng).transform(CircleTransform()).into(img)
                company.text = "全峰快递:" + expresses[position].orderNo
            }
            "debangwuliu" -> {
                Picasso.with(context).load(R.drawable.pic_debang).transform(CircleTransform()).into(img)
                company.text = "德邦物流:" + expresses[position].orderNo
            }
            "zhaijisong" -> {
                Picasso.with(context).load(R.drawable.pic_zhaijisong).transform(CircleTransform()).into(img)
                company.text = "宅急送:" + expresses[position].orderNo
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