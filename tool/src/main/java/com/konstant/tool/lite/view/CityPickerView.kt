package com.konstant.tool.lite.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.konstant.tool.lite.R
import com.konstant.tool.lite.data.AreaManager
import com.konstant.tool.lite.data.bean.weather.China
import kotlinx.android.synthetic.main.layout_dialog_city_selector.view.*

/**
 * 时间：2019/8/27 15:14
 * 创建：菜籽
 * 描述：城市选择器
 */

class CityPickerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleStr: Int = 0) : RelativeLayout(context, attrs, defStyleStr) {

    private val mChina = AreaManager.getChina()
    private var mSelectedDirect = mChina.provinceList[0].cityList[0].countyList[0]
    private var mOnCancelClick: (() -> Unit)? = null
    private var mOnConfirmClick: ((direct: China.Province.City.County) -> Unit)? = null
    private val mRoot by lazy { LayoutInflater.from(context).inflate(R.layout.layout_dialog_city_selector, null) }

    init {
        mRoot.picker_direct.data = mChina.provinceList[0].cityList[0].countyList.map { it.name }
        mRoot.btn_cancel.setOnClickListener { mOnCancelClick?.invoke() }
        mRoot.btn_confirm.setOnClickListener { mOnConfirmClick?.invoke(mSelectedDirect) }

        var selectedProvince = mChina.provinceList[0]
        var selectedCity = mChina.provinceList[0].cityList[0]
        mRoot.picker_province.apply {
            data = mChina.provinceList.map { it.name }
            setOnItemSelectedListener { _, _, position ->
                selectedProvince = mChina.provinceList[position]
                selectedCity = selectedProvince.cityList[0]
                mSelectedDirect = selectedCity.countyList[0]
                mRoot.picker_city.data = selectedProvince.cityList.map { it.name }
                mRoot.picker_city.selectedItemPosition = 0
                mRoot.picker_direct.data = selectedCity.countyList.map { it.name }
            }
        }

        mRoot.picker_city.apply {
            data = selectedProvince.cityList.map { it.name }
            setOnItemSelectedListener { _, _, position ->
                selectedCity = selectedProvince.cityList[position]
                mSelectedDirect = selectedCity.countyList[0]
                mRoot.picker_direct.data = selectedCity.countyList.map { it.name }
                mRoot.picker_direct.selectedItemPosition = 0
            }
        }

        mRoot.picker_direct.setOnItemSelectedListener { _, data, position ->
            mSelectedDirect = selectedCity.countyList[position]
        }

        addView(mRoot)
    }

    fun setOnCancelClick(onCancelClick: (() -> Unit)) {
        mOnCancelClick = onCancelClick
    }

    fun setOnConfirmClick(onConfirmClick: ((direct: China.Province.City.County) -> Unit)) {
        mOnConfirmClick = onConfirmClick
    }

}
