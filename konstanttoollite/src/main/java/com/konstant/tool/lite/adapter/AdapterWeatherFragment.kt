package com.konstant.tool.lite.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

/**
 * 描述:fragment的适配器
 * 创建人:菜籽
 * 创建时间:2017/12/28 下午6:34
 * 备注:
 */

class AdapterWeatherFragment(val fragmentList: List<Fragment>,fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getItem(position: Int): Fragment = fragmentList[position]


    override fun getCount(): Int = fragmentList.size


}