package com.konstant.tool.lite.module.date

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.util.DateUtil
import com.konstant.tool.lite.view.DatePickerView
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.fragment_calculation.*

/**
 * 时间：2019/9/29 14:15
 * 创建：菜籽
 * 描述：计算当前日期往前或者往后指定天数的日期
 */

class CalculationFragment : BaseFragment() {

    private val mForward by lazy { context?.getString(R.string.date_calculate_forward) }
    private val mBackward by lazy { context?.getString(R.string.date_calculate_backward) }

    companion object {
        @JvmStatic
        fun newInstance() = CalculationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_calculation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout_content.setOnClickListener { hideSoftKeyboard() }
        val date = DateUtil.getCurrentDate(DateUtil.FORMAT)
        tv_start.text = date
        tv_start.setOnClickListener {
            val dialog = KonstantDialog(view.context)
            val pickerView = DatePickerView(view.context, date = tv_start.text.toString()).apply {
                setOnCancelClick { dialog.dismiss() }
                setOnConfirmClick {
                    tv_start.text = it
                    dialog.dismiss()
                }
            }
            dialog.addView(pickerView).hideNavigation().createDialog()
        }
        btn_direction.setOnClickListener {
            if (TextUtils.equals(btn_direction.text.toString(), mForward)) {
                btn_direction.text = mBackward
            } else {
                btn_direction.text = mForward
            }
            calculateDate()
        }
        btn_calculate.setOnClickListener { calculateDate() }
    }

    private fun calculateDate(){
        val number: Int = try {
            et_input.text.toString().toInt()
        } catch (e: Exception) {
            0
        }
        val days = if (TextUtils.equals(btn_direction.text.toString(), mForward)) 0 - number else number
        val date = DateUtil.calculateLaterDate(tv_start.text.toString(), days)
        tv_result.text = date
    }

}
