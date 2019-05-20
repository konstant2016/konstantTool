package com.konstant.tool.lite.module.express.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.express.server.ExpressData
import com.konstant.tool.lite.util.CircleTransform
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_express_list.view.*

/**
 * 描述:快递列表的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:08
 * 备注:
 */

class AdapterExpressList(val context: Context, val expresses: ArrayList<ExpressData>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val data = expresses[position]
        return LayoutInflater.from(context).inflate(R.layout.item_express_list, parent, false)
                .apply {
                    Picasso.with(context).load(getDrawableId(data.company)).transform(CircleTransform()).into(img_company)
                    tv_express_num.text = data.company + ":" + data.number
                    tv_name.text = data.name
                    tv_state.text = if (TextUtils.isEmpty(data.status)) "暂无信息" else data.status
                }
    }


    private fun getDrawableId(company: String): Int {
        if (company.contains("天天"))
            return R.drawable.pic_tiantian
        if (company.contains("顺丰"))
            return R.drawable.pic_shunfeng
        if (company.contains("邮政") or company.contains("包裹"))
            return R.drawable.pic_ems
        if (company.contains("中通"))
            return R.drawable.pic_zhongtong
        if (company.contains("圆通"))
            return R.drawable.pic_yuantong
        if (company.contains("申通"))
            return R.drawable.pic_shentong
        if (company.contains("韵达"))
            return R.drawable.pic_yunda
        if (company.contains("汇通") or company.contains("百世"))
            return R.drawable.pic_huiotng
        if (company.contains("全峰"))
            return R.drawable.pic_quanfeng
        if (company.contains("德邦"))
            return R.drawable.pic_debang
        if (company.contains("宅"))
            return R.drawable.pic_zhaijisong
        return R.drawable.ic_launcher
    }

    override fun getItem(position: Int): Any = expresses[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = expresses.size

}