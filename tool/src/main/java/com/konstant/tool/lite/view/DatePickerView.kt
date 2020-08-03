package com.konstant.tool.lite.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.konstant.tool.lite.R
import com.konstant.tool.lite.util.DateUtil
import kotlinx.android.synthetic.main.layout_dialog_date_selector.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * 时间：2019/9/29 15:52
 * 创建：菜籽
 * 描述：滚轮日期选择器
 */

@SuppressLint("SimpleDateFormat")
class DatePickerView @JvmOverloads constructor(context: Context, date: String? = null, attrs: AttributeSet? = null, defStyleStr: Int = 0) :
        RelativeLayout(context, attrs, defStyleStr) {

    private var mYear = SimpleDateFormat("yyyy").format(Date())
    private var mMonth = SimpleDateFormat("MM").format(Date())
    private var mDay = SimpleDateFormat("dd").format(Date())

    private val mRootView by lazy { LayoutInflater.from(context).inflate(R.layout.layout_dialog_date_selector, null) }

    private var mOnCancelClick: (() -> Unit)? = null
    private var mOnConfirmClick: ((date: String) -> Unit)? = null

    init {
        val years = getFillData(1901, 2099, "年")
        val months = getFillData(1, 12, "月")
        val days = getFillData(1, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH), "日")

        date?.let {
            mYear = DateUtil.getYearWithDate(date)
            mMonth = DateUtil.getMonthWithDate(date)
            mDay = DateUtil.getDayWithDate(date)
        }

        updateDate()

        mRootView.btn_cancel.setOnClickListener { mOnCancelClick?.invoke() }
        mRootView.btn_confirm.setOnClickListener {
            mOnConfirmClick?.invoke(mRootView.tv_date.text.toString())
        }

        mRootView.picker_year.apply {
            data = years
            years.forEachIndexed { index, string ->
                if (string.contains("$mYear")) this.selectedItemPosition = index
            }
            setOnItemSelectedListener { _, data, _ ->
                mYear = getNumberFromString(data.toString())
                updateDate()
                updateDays()
            }
        }

        mRootView.picker_month.apply {
            data = months
            months.forEachIndexed { index, string ->
                if (string.contains("$mMonth")) this.selectedItemPosition = index
            }
            setOnItemSelectedListener { _, data, _ ->
                mMonth = getNumberFromString(data.toString())
                updateDate()
                updateDays()
            }
        }

        mRootView.picker_day.apply {
            data = days
            days.forEachIndexed { index, string ->
                if (string.contains("$mDay")) this.selectedItemPosition = index
            }
            setOnItemSelectedListener { _, data, _ ->
                mDay = getNumberFromString(data.toString())
                updateDate()
            }
        }

        addView(mRootView)
    }

    // 更新天数
    private fun updateDays() {
        val calendar = Calendar.getInstance()
        calendar.time = SimpleDateFormat("yyyy-MM-dd").parse("$mYear-$mMonth-1")
        val days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val fillData = getFillData(1, days, "日")
        mRootView.picker_day.data = fillData
    }

    // 更新时间
    private fun updateDate() {
        mRootView.tv_date.text = "${mYear}年${mMonth}月${mDay}日"
    }

    // 获取填充的list数据
    private fun getFillData(start: Int, end: Int, unit: String): List<String> {
        val list = ArrayList<String>()
        for (index in start..end) {
            if (index < 10) {
                list.add("0$index$unit")
            } else {
                list.add("$index$unit")
            }
        }
        return list
    }

    // 从选中的位置中取出当前的int值
    private fun getNumberFromString(str: String) = str
            .replace("年", "")
            .replace("月", "")
            .replace("日", "")

    fun setOnCancelClick(onCancelClick: (() -> Unit)) {
        mOnCancelClick = onCancelClick
    }

    fun setOnConfirmClick(onConfirmClick: ((date: String) -> Unit)) {
        mOnConfirmClick = onConfirmClick
    }

}