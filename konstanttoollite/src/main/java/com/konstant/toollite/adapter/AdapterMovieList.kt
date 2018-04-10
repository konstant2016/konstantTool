package com.konstant.toollite.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class AdapterMovieList(val context: Context,val list: List<MovieListResponse.Data.Movie>) : RecyclerView.Adapter<AdapterMovieList.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AdapterMovieList.Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_movie, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AdapterMovieList.Holder, position: Int) {
        val img = holder.view.findViewById<ImageView>(R.id.img_pic)
        val name = holder.view.findViewById<TextView>(R.id.tv_name)
        val dir = holder.view.findViewById<TextView>(R.id.tv_dir)
        val type = holder.view.findViewById<TextView>(R.id.tv_type)
        val rate = holder.view.findViewById<TextView>(R.id.tv_rate)

        val movie = list[position]

        Picasso.with(context).load(movie.cover).into(img)
        name.text = movie.title
        dir.text = if (movie.directors.size==0) "未知" else movie.directors[0]
        type.text = movie.movieTypes[0]
        rate.text = movie.rate.rate

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}