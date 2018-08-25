package com.konstant.tool.lite.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * 时间：2018/8/2 19:12
 * 作者：吕卡
 * 描述：
 */
class BaseFragmentAdapter(fm: FragmentManager, val fragmentList: List<BaseFragment>,
                          val titleList: List<String> = listOf())
    : FragmentPagerAdapter(fm) {

    private val mFragPosOldMap = HashMap<Long, String>()
    private val mFragPosNewMap = HashMap<Long, String>()

    init {
        setFragmentPositionOldMap()
        setFragmentPositionNewMap()
    }

    private fun setFragmentPositionOldMap() {
        mFragPosOldMap.clear()
        fragmentList.forEachIndexed { index, _ ->
            mFragPosOldMap[getItemId(index)] = "$index"
        }
    }

    private fun setFragmentPositionNewMap() {
        mFragPosNewMap.clear()
        fragmentList.forEachIndexed { index, _ ->
            mFragPosNewMap[getItemId(index)] = "$index"
        }
    }


    override fun getItem(position: Int): Fragment = fragmentList[position]


    override fun getCount(): Int = fragmentList.size


    override fun getItemPosition(any: Any): Int {
        var result = POSITION_NONE

        val position = mFragPosNewMap[any.hashCode().toLong()]
        if (position.isNullOrEmpty()) {
            return result
        }

        mFragPosOldMap.forEach { key, _ ->
            if (key == any.hashCode().toLong() && mFragPosOldMap[key] == position) {
                result = POSITION_UNCHANGED
                return@forEach
            }
        }

        return result
    }


    // 取代直接返回position，用于识别是否为同一个fragment
    override fun getItemId(position: Int) = fragmentList[position].hashCode().toLong()


    override fun notifyDataSetChanged() {
        setFragmentPositionNewMap()
        super.notifyDataSetChanged()
        setFragmentPositionOldMap()
    }

    override fun getPageTitle(position: Int): CharSequence = titleList[position]
}