package com.konstant.toollite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.konstant.toollite.R
import com.konstant.toollite.server.response.MovieListResponse
import com.squareup.picasso.Picasso

/**
 * 描述:电影预告片的适配器
 * 创建人:菜籽
 * 创建时间:2018/4/10 下午7:16
 * 备注:
 */

class AdapterMovieList(val context: Context, val list: List<MovieListResponse.Data.Movie>) : BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = list.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var root = convertView
        lateinit var holder: Holder
        if (root == null) {
            root = inflater.inflate(R.layout.item_grid_movie, null)
            val img = root.findViewById<ImageView>(R.id.img_pic)
            val name = root.findViewById<TextView>(R.id.tv_name)
            val dir = root.findViewById<TextView>(R.id.tv_dir)
            val type = root.findViewById<TextView>(R.id.tv_type)
            val rate = root.findViewById<TextView>(R.id.tv_rate)
            holder = Holder(img, name, dir, type, rate)
            root.tag = holder
        } else {
            holder = root.tag as Holder
        }

        val movie = list[position]

        Picasso.with(context).load(movie.cover).into(holder.img)
        holder.name.text = movie.title
        holder.dir.text = if (movie.directors.size == 0) "未知" else movie.directors[0]
        holder.type.text = movie.movieTypes[0]
        holder.rate.text = movie.rate.rate

        return root!!
    }

    class Holder(val img: ImageView,
                 val name: TextView,
                 val dir: TextView,
                 val type: TextView,
                 val rate: TextView)

}