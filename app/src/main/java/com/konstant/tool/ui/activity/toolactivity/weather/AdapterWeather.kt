package com.konstant.tool.ui.activity.toolactivity.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.konstant.tool.R
import com.konstant.tool.server.response.WeatherResponse

/**
 * 描述:适配器
 * 创建人:菜籽
 * 创建时间:2017/12/29 下午5:13
 * 备注:
 */

class AdapterWeather(val context: Context, val list: List<WeatherResponse.HeWeather.DailyForecast>) : BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView
        var holder: ViewHolder
        if (view == null) {
            view = inflater.inflate(R.layout.item_weather_list_view, parent, false)
            holder = ViewHolder()
            holder.tv_date = view.findViewById(R.id.tv_date) as TextView
            holder.tv_weather = view.findViewById(R.id.tv_weather) as TextView
            holder.tv_wind_sc = view.findViewById(R.id.tv_wind_sc) as TextView
            holder.tv_pop = view.findViewById(R.id.tv_pop) as TextView
            holder.tv_tmp_max = view.findViewById(R.id.tv_tmp_max) as TextView
            holder.tv_tmp_min = view.findViewById(R.id.tv_tmp_min) as TextView
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        val weather = list[position]
        holder.tv_date.text = weather.date
        holder.tv_weather.text = weather.cond_txt_d
        holder.tv_wind_sc.text = "风力等级：" + weather.wind_sc
        holder.tv_pop.text = "降水概率：${weather.pop}%"
        holder.tv_tmp_max.text = "最高温度：${weather.tmp_max}°"
        holder.tv_tmp_min.text = "最低温度：${weather.tmp_min}°"
        return view!!
    }

    class ViewHolder {
        lateinit var tv_date: TextView
        lateinit var tv_weather: TextView

        lateinit var tv_wind_sc: TextView
        lateinit var tv_pop: TextView
        lateinit var tv_tmp_max: TextView
        lateinit var tv_tmp_min: TextView
    }
}