package com.konstant.tool.lite.module.date

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment

/**
* 时间：2019/9/29 14:15
* 创建：菜籽
* 描述：计算当前日期往前或者往后指定天数的日期
*/

class CalculationFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = CalculationFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_calculation, container, false)

}
