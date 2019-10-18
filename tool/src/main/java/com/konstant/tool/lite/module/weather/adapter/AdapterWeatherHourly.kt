package com.konstant.tool.lite.module.weather.adapter

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.squareup.picasso.Picasso

/**
 * 描述:逐小时预报的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午8:05
 * 备注:
 */

class AdapterWeatherHourly(private val context: Context, private val datas: List<WeatherResponse.HourlyForecastBean>) : androidx.recyclerview.widget.RecyclerView.Adapter<AdapterWeatherHourly.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_24_hour, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = datas.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = datas[position]

        val tTime = holder.view.findViewById(R.id.tv_time) as TextView
        val img = holder.view.findViewById(R.id.img_weather) as ImageView
        val tTem = holder.view.findViewById(R.id.tv_temperature) as TextView

        tTime.text = "${data.hour}:00"
        tTem.text = "${data.temperature}℃"

        val hour = data.hour.toInt()
        when (data.img) {
            0 -> {
                if (hour in 6..18) {
                    Picasso.with(context).load(R.drawable.weather_img_1).into(img)
                } else {
                    Picasso.with(context).load(R.drawable.weather_img_1_night).into(img)
                }
            }
            1 -> {
                if (hour in 6..18) {
                    Picasso.with(context).load(R.drawable.weather_img_2).into(img)
                } else {
                    Picasso.with(context).load(R.drawable.weather_img_2_night).into(img)
                }
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

    class Holder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

}