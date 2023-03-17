package com.konstant.tool.lite.module.stock.history

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AdapterViewPager(manager: FragmentManager, private val fragmentList: List<Fragment>) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size
}