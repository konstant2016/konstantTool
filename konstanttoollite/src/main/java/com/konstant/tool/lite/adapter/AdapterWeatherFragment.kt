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

class AdapterWeatherFragment(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    private val mFragmentList = mutableListOf<Fragment>()


    override fun getItem(position: Int): Fragment = mFragmentList[position]


    override fun getCount(): Int = mFragmentList.size


    override fun getItemPosition(any: Any?): Int = PagerAdapter.POSITION_NONE


    fun updateFragmentList(fragmentList: List<Fragment>){
        mFragmentList.clear()
        mFragmentList.addAll(fragmentList)
        notifyDataSetChanged()
    }

}