package com.konstant.tool.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 描述:fragment的适配器
 * 创建人:菜籽
 * 创建时间:2017/12/28 下午6:34
 * 备注:
 */

class AdapterViewpager(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {


    private val mFragmentList = mutableListOf<androidx.fragment.app.Fragment>()


    override fun getItem(position: Int): androidx.fragment.app.Fragment = mFragmentList[position]


    override fun getCount(): Int = mFragmentList.size


    override fun getItemPosition(any: Any): Int = androidx.viewpager.widget.PagerAdapter.POSITION_NONE


    fun updateFragmentList(fragmentList: List<androidx.fragment.app.Fragment>){
        mFragmentList.clear()
        mFragmentList.addAll(fragmentList)
        notifyDataSetChanged()
    }

}