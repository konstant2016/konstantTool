package com.konstant.tool.lite.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.server.response.Weather360Response
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

/**
 * 描述:逐天预报的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午10:24
 * 备注:
 */

class AdapterWeatherDaily(private val context: Context, private val datas: List<Weather360Response.WeatherBean>) : RecyclerView.Adapter<AdapterWeatherDaily.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AdapterWeatherDaily.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_weather_15_daily, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = datas.size


    override fun onBindViewHolder(holder: AdapterWeatherDaily.Holder, position: Int) {
        val data = datas[position]

        val tWeeak = holder.view.findViewById<TextView>(R.id.tv_weather_week)
        val tDate = holder.view.findViewById<TextView>(R.id.tv_weather_date)
        val tInfo = holder.view.findViewById<TextView>(R.id.tv_weather_info)
        val tTemp = holder.view.findViewById<TextView>(R.id.tv_weather_temperature)
        val tDriect = holder.view.findViewById<TextView>(R.id.tv_weather_direct)
        val tPower = holder.view.findViewById<TextView>(R.id.tv_weather_power)
        val img = holder.view.findViewById<ImageView>(R.id.img_weather_icon)

        val date = SimpleDateFormat("yyyy-MM-dd").parse(data.date)
//        val date = Date()
        val week = SimpleDateFormat("E").format(date)

        // 设置周几
        tWeeak.text = week

        // 设置日期
        val split = data.date.replace("-", "/").split("/")
        tDate.text = "${split[1]}/${split[2]}"

        // 设置多云转晴
        if (data.info.day[1] == data.info.night[1]) {
            tInfo.text = data.info.day[1]
        } else {
            tInfo.text = "${data.info.day[1]}转${data.info.night[1]}"
        }

        // 设置温度
        tTemp.text = "${data.info.day[2]}~${data.info.night[2]}℃"

        // 设置风向
        tDriect.text = data.info.day[3]

        // 设置风级
        tPower.text = data.info.day[4]

        // 设置图片
        when (data.info.day[0].toInt()) {
            0 -> {
                Picasso.with(context).load(R.drawable.weather_img_1).into(img)
            }
            1 -> {
                Picasso.with(context).load(R.drawable.weather_img_2).into(img)
            }
            2 -> {
                Picasso.with(context).load(R.drawable.weather_img_3).into(img)
            }
            3 -> {
                Picasso.with(context).load(R.drawable.weather_img_4).into(img)
            }
            4 -> {
                Picasso.with(context).load(R.drawable.weather_img_5).into(img)
            }
            5 -> {
                Picasso.with(context).load(R.drawable.weather_img_6).into(img)
            }
            6 -> {
                Picasso.with(context).load(R.drawable.weather_img_7).into(img)
            }
            7 -> {
                Picasso.with(context).load(R.drawable.weather_img_8).into(img)
            }
            8 -> {
                Picasso.with(context).load(R.drawable.weather_img_9).into(img)
            }
            9 -> {
                Picasso.with(context).load(R.drawable.weather_img_10).into(img)
            }
            10 -> {
                Picasso.with(context).load(R.drawable.weather_img_11).into(img)
            }
            11 -> {
                Picasso.with(context).load(R.drawable.weather_img_12).into(img)
            }
            12 -> {
                Picasso.with(context).load(R.drawable.weather_img_13).into(img)
            }
            13 -> {
                Picasso.with(context).load(R.drawable.weather_img_14).into(img)
            }
            14 -> {
                Picasso.with(context).load(R.drawable.weather_img_15).into(img)
            }
            15 -> {
                Picasso.with(context).load(R.drawable.weather_img_16).into(img)
            }
            30 -> {
                Picasso.with(context).load(R.drawable.weather_img_17).into(img)
            }
            17 -> {
                Picasso.with(context).load(R.drawable.weather_img_18).into(img)
            }
        }
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}