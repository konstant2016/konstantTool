package com.konstant.tool.lite.module.date


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.util.DateUtil
import com.konstant.tool.lite.view.DatePickerView
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.fragment_interval.*

/**
 * 时间：2019/9/29 14:15
 * 创建：菜籽
 * 描述：计算两个日期之间的间隔
 */
class IntervalFragment : BaseFragment() {

    companion object {
        fun newInstance() = IntervalFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_interval, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val date = DateUtil.getCurrentDate(DateUtil.FORMAT)
        tv_start.text = date
        tv_end.text = date
        tv_start.setOnClickListener {
            val dialog = KonstantDialog(view.context)
            val pickerView = DatePickerView(view.context, date = tv_start.text.toString()).apply {
                setOnCancelClick { dialog.dismiss() }
                setOnConfirmClick {
                    tv_start.text = it
                    dialog.dismiss()
                    val days = DateUtil.calculateDateInterval(tv_start.text.toString(), tv_end.text.toString())
                    tv_result.text = "$days 天"
                }
            }
            dialog.addView(pickerView).hideNavigation().createDialog()
        }

        tv_end.setOnClickListener {
            val dialog = KonstantDialog(view.context)
            val pickerView = DatePickerView(view.context, date = tv_end.text.toString()).apply {
                setOnCancelClick { dialog.dismiss() }
                setOnConfirmClick {
                    tv_end.text = it
                    dialog.dismiss()
                    val days = DateUtil.calculateDateInterval(tv_start.text.toString(), tv_end.text.toString())
                    tv_result.text = "$days 天"
                }
            }
            dialog.addView(pickerView).hideNavigation().createDialog()
        }
    }

}
