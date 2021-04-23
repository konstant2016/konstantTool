package com.konstant.tool.lite.module.weather.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.H5Activity
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.weather.data.WeatherData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_weather_current.view.*
import kotlinx.android.synthetic.main.item_weather_days.view.*
import kotlinx.android.synthetic.main.item_weather_life.view.*
import kotlinx.android.synthetic.main.item_weather_title.view.*

class AdapterWeatherFragment(private val mList: ArrayList<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_CURRENT = 1
    private val TYPE_HOUR = 2
    private val TYPE_DAY = 3
    private val TYPE_LIFE = 4
    private val TYPE_TITLE = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CURRENT -> CurrentHolder(inflater.inflate(R.layout.item_weather_current, parent, false))
            TYPE_TITLE -> TitleHolder(inflater.inflate(R.layout.item_weather_title, parent, false))
            TYPE_HOUR -> HourHolder(inflater.inflate(R.layout.item_weather_hours, parent, false))
            TYPE_DAY -> DayHolder(inflater.inflate(R.layout.item_weather_days, parent, false))
            else -> LifeHolder(inflater.inflate(R.layout.item_weather_life, parent, false))
        }
    }

    override fun getItemCount() = mList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrentHolder -> setCurrent(holder, mList[position] as WeatherData.Current)
            is TitleHolder -> setTitle(holder, mList[position] as WeatherData.Title)
            is DayHolder -> setDays(holder, mList[position] as WeatherData.Day)
            is LifeHolder -> setLife(holder, mList[position] as WeatherData.Life.LifeData)
            is HourHolder -> setHours(holder, mList[position] as WeatherData.Hour)
        }
    }

    private fun setCurrent(holder: CurrentHolder, current: WeatherData.Current) {
        holder.itemView.apply {
            if (TextUtils.isEmpty(current.alert)) {
                tv_weather_update_time.text = current.updateTime
            } else {
                tv_weather_update_time.text = current.alert
                tv_weather_update_time.setOnClickListener {
                    H5Activity.openWebView(context,current.alertUrl,false)
                }
            }
            tv_current_direct.text = current.direction
            tv_current_temperature.text = "${current.temperature}"
            tv_current_describe.text = current.weather
            tv_current_power.text = current.power
        }
    }

    private fun setTitle(holder: TitleHolder, title: WeatherData.Title) {
        holder.itemView.tv_title_title.text = title.title
    }

    private fun setHours(holder: HourHolder, day: WeatherData.Hour) {
        val adapter = AdapterWeatherHour(day.hourDataList)
        (holder.itemView as RecyclerView).apply {
            layoutManager = LinearLayoutManager(KonApplication.context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(adapter)
        }
    }

    private fun setDays(holder: DayHolder, day: WeatherData.Day) {
        holder.itemView.apply {
            tv_weather_week.text = day.week
            tv_weather_date.text = day.date
            tv_weather_info.text = day.weather
            tv_weather_temperature.text = day.temperature
            tv_weather_direct.text = day.direction
            tv_weather_power.text = day.power
            when (day.imgType) {
                0 -> {
                    Picasso.get().load(R.drawable.weather_img_1).into(img_weather_icon)
                }
                1 -> {
                    Picasso.get().load(R.drawable.weather_img_2).into(img_weather_icon)
                }
                2 -> {
                    Picasso.get().load(R.drawable.weather_img_3).into(img_weather_icon)
                }
                3 -> {
                    Picasso.get().load(R.drawable.weather_img_4).into(img_weather_icon)
                }
                4 -> {
                    Picasso.get().load(R.drawable.weather_img_5).into(img_weather_icon)
                }
                5 -> {
                    Picasso.get().load(R.drawable.weather_img_6).into(img_weather_icon)
                }
                6 -> {
                    Picasso.get().load(R.drawable.weather_img_7).into(img_weather_icon)
                }
                7 -> {
                    Picasso.get().load(R.drawable.weather_img_8).into(img_weather_icon)
                }
                8 -> {
                    Picasso.get().load(R.drawable.weather_img_9).into(img_weather_icon)
                }
                9 -> {
                    Picasso.get().load(R.drawable.weather_img_10).into(img_weather_icon)
                }
                10 -> {
                    Picasso.get().load(R.drawable.weather_img_11).into(img_weather_icon)
                }
                11 -> {
                    Picasso.get().load(R.drawable.weather_img_12).into(img_weather_icon)
                }
                12 -> {
                    Picasso.get().load(R.drawable.weather_img_13).into(img_weather_icon)
                }
                13 -> {
                    Picasso.get().load(R.drawable.weather_img_14).into(img_weather_icon)
                }
                14 -> {
                    Picasso.get().load(R.drawable.weather_img_15).into(img_weather_icon)
                }
                15, 18 -> {
                    Picasso.get().load(R.drawable.weather_img_16).into(img_weather_icon)
                }
                30, 33 -> {
                    Picasso.get().load(R.drawable.weather_img_17).into(img_weather_icon)
                }
                17 -> {
                    Picasso.get().load(R.drawable.weather_img_18).into(img_weather_icon)
                }
            }
        }
    }

    private fun setLife(holder: LifeHolder, life: WeatherData.Life.LifeData) {
        holder.itemView.apply {
            tv_life_title.text = life.title
            tv_life_describe.text = life.describe
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList[position]) {
            is WeatherData.Current -> TYPE_CURRENT
            is WeatherData.Title -> TYPE_TITLE
            is WeatherData.Hour -> TYPE_HOUR
            is WeatherData.Day -> TYPE_DAY
            else -> TYPE_LIFE
        }
    }

    class CurrentHolder(view: View) : RecyclerView.ViewHolder(view)

    class HourHolder(view: View) : RecyclerView.ViewHolder(view)

    class DayHolder(view: View) : RecyclerView.ViewHolder(view)

    class LifeHolder(view: View) : RecyclerView.ViewHolder(view)

    class TitleHolder(view: View) : RecyclerView.ViewHolder(view)
}

