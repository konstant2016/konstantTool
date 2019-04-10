package com.konstant.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val list = ArrayList<Data>()
    val mAdapter by lazy { Adapter(list, this@MainActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = assets.open("wallpaper.txt").bufferedReader().readText()

        with(recycler) {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        RxJava().rxJava02(object :RxJava.Result{
            override fun onSuccess(list: Weather) {
                tv.text = list.alert.toString()
            }

            override fun onError() {

            }
        })

    }
}
