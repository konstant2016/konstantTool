package com.konstant.tool.lite.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 时间：2018/8/2 19:12
 * 作者：吕卡
 * 描述：
 */
class BaseFragmentAdapter(fm: androidx.fragment.app.FragmentManager, private val fragmentList: List<BaseFragment>, private val titleList: List<String> = listOf()) : androidx.fragment.app.FragmentPagerAdapter(fm) {

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

    override fun getItem(position: Int): androidx.fragment.app.Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getItemPosition(any: Any): Int {
        var result = POSITION_NONE

        val position = mFragPosNewMap[any.hashCode().toLong()]
        if (position.isNullOrEmpty()) {
            return result
        }

        for (entry in mFragPosOldMap) {
            if (entry.key == any.hashCode().toLong() && mFragPosOldMap[entry.key] == position){
                result = POSITION_UNCHANGED
                break
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