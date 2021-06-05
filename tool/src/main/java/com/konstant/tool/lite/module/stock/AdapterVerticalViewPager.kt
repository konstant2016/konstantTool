package com.konstant.tool.lite.module.stock

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 时间：2021/6/5 20:50
 * 作者：吕卡
 * 备注：垂直的ViewPager的适配器
 */

class AdapterVerticalViewPager(manager: FragmentManager, lifeCycle: Lifecycle, private val fragmentList: List<Fragment>) : FragmentStateAdapter(manager, lifeCycle) {

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int) = fragmentList[position]
}