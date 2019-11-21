package com.konstant.tool.lite.module.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.module.weather.data.WeatherData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_weather_hour.view.*

/**
 * 描述:逐小时预报的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午8:05
 * 备注:
 */

class AdapterWeatherHour(private val hours: List<WeatherData.Hour.HourData>) : RecyclerView.Adapter<AdapterWeatherHour.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather_hour, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = hours.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val hourData = hours[position]
        holder.itemView.apply {
            tv_time.text = "${hourData.hour}:00"
            tv_temperature.text = hourData.temperature
            when (hourData.imgType) {
                0 -> {
                    if (hourData.hour in 6..18) {
                        Picasso.get().load(R.drawable.weather_img_1).into(img_weather)
                    } else {
                        Picasso.get().load(R.drawable.weather_img_1_night).into(img_weather)
                    }
                }
                1 -> {
                    if (hourData.hour in 6..18) {
                        Picasso.get().load(R.drawable.weather_img_2).into(img_weather)
                    } else {
                        Picasso.get().load(R.drawable.weather_img_2_night).into(img_weather)
                    }
                }
                2 -> {
                    Picasso.get().load(R.drawable.weather_img_3).into(img_weather)
                }
                3 -> {
                    Picasso.get().load(R.drawable.weather_img_4).into(img_weather)
                }
                4 -> {
                    Picasso.get().load(R.drawable.weather_img_5).into(img_weather)
                }
                5 -> {
                    Picasso.get().load(R.drawable.weather_img_6).into(img_weather)
                }
                6 -> {
                    Picasso.get().load(R.drawable.weather_img_7).into(img_weather)
                }
                7 -> {
                    Picasso.get().load(R.drawable.weather_img_8).into(img_weather)
                }
                8 -> {
                    Picasso.get().load(R.drawable.weather_img_9).into(img_weather)
                }
                9 -> {
                    Picasso.get().load(R.drawable.weather_img_10).into(img_weather)
                }
                10 -> {
                    Picasso.get().load(R.drawable.weather_img_11).into(img_weather)
                }
                11 -> {
                    Picasso.get().load(R.drawable.weather_img_12).into(img_weather)
                }
                12 -> {
                    Picasso.get().load(R.drawable.weather_img_13).into(img_weather)
                }
                13 -> {
                    Picasso.get().load(R.drawable.weather_img_14).into(img_weather)
                }
                14 -> {
                    Picasso.get().load(R.drawable.weather_img_15).into(img_weather)
                }
                15 -> {
                    Picasso.get().load(R.drawable.weather_img_16).into(img_weather)
                }
                30 -> {
                    Picasso.get().load(R.drawable.weather_img_17).into(img_weather)
                }
                17 -> {
                    Picasso.get().load(R.drawable.weather_img_18).into(img_weather)
                }
            }
        }
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view)

}