package com.konstant.tool.lite.base

import android.util.LongSparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 时间：2018/8/2 19:12
 * 作者：菜籽
 * 描述：
 */

class BaseFragmentAdapter(fm: FragmentManager, private val fragmentList: List<BaseFragment>, private val titleList: List<String> = listOf()) :
        FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val mFragPosOldArray = LongSparseArray<String>()
    private val mFragPosNewArray = LongSparseArray<String>()

    init {
        setFragmentPositionOldMap()
        setFragmentPositionNewMap()
    }

    private fun setFragmentPositionOldMap() {
        mFragPosOldArray.clear()
        fragmentList.forEachIndexed { index, _ ->
            mFragPosOldArray.put(getItemId(index), "$index")
        }
    }

    private fun setFragmentPositionNewMap() {
        mFragPosNewArray.clear()
        fragmentList.forEachIndexed { index, _ ->
            mFragPosNewArray.put(getItemId(index), "$index")
        }
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    /**
     * 此项用于返回 此Fragment是否发生变化，
     * POSITION_UNCHANGED : 表示无变化，不需要刷新数据
     * POSITION_NONE ：表示不存在，需要重建刷新
     * 从新的fragmentArray中根据hashCode获取index
     * 如果旧的fragmentArray中也有对应的fragment，并且index相等，那么这个fragment就没有变化，不用重新绘制
     * 否则的话，就重新绘制
     */
    override fun getItemPosition(any: Any): Int {
        val index = any.hashCode().toLong()
        val position = mFragPosNewArray[index]
        if (position.isNullOrEmpty()) {
            return POSITION_NONE
        }
        if (mFragPosOldArray[index] != null && mFragPosOldArray[index] == position) {
            return POSITION_UNCHANGED
        }
        return POSITION_NONE
    }

    // 取代直接返回position，用于识别是否为同一个fragment
    override fun getItemId(position: Int): Long {
        return fragmentList[position].hashCode().toLong()
    }

    override fun notifyDataSetChanged() {
        setFragmentPositionNewMap()
        super.notifyDataSetChanged()
        setFragmentPositionOldMap()
    }

    override fun getPageTitle(position: Int): CharSequence = titleList[position]
}