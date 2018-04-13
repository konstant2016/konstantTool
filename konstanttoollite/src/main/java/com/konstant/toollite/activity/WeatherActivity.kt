package com.konstant.toollite.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterWeatherFragment
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.data.LocalDirectManager
import com.konstant.toollite.eventbusparam.IndexChanged
import com.konstant.toollite.eventbusparam.LocationSizeChanged
import com.konstant.toollite.eventbusparam.TitleChanged
import com.konstant.toollite.fragment.WeatherFragment
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.Subscribe

/**
 * 描述:天气预报主页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:21
 * 备注:
 */


@SuppressLint("MissingSuperCall")
class WeatherActivity : BaseActivity() {

    private val mFragmentList = mutableListOf<Fragment>()
    private var mSavedInstanceState: Bundle? = null
    private val mAdapter by lazy { AdapterWeatherFragment(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSavedInstanceState = savedInstanceState
        setContentView(R.layout.activity_weather)
        setTitle("天气查询")
        initBaseViews()
    }


    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { startActivity(Intent(this, CityManagerActivity::class.java)) }
        layout_viewpager.offscreenPageLimit = 50
        layout_viewpager.adapter = mAdapter

        addFragment()
    }

    private fun addFragment(){

        val weatherCodeList = LocalDirectManager.readCityList(this)
        if (weatherCodeList.size > 0) {
            setSubTitle("左右滑动可切换城市")
        }else{
            hideSubTitle()
        }

        mFragmentList.clear()
        mFragmentList.add(WeatherFragment.newInstance(""))
        weatherCodeList.forEach {
            mFragmentList.add(WeatherFragment.newInstance("${it.cityCode}"))
        }
        mAdapter.updateFragmentList(mFragmentList)
        Log.i("mFragmentList size", "${mFragmentList.size}")
    }

    @Subscribe
    fun onTitleChanged(msg: TitleChanged) {
        setTitle(msg.title)
    }

    @Subscribe
    fun onIndexChanged(msg:IndexChanged){
        layout_viewpager.currentItem = msg.index
    }

    @Subscribe
    fun onLocationSizeChanged(msg: LocationSizeChanged){
        addFragment()
    }

}


