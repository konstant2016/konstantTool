package com.konstant.konstanttools.ui.activity.toolactivity.weather

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import com.aigestudio.wheelpicker.WheelPicker
import com.alibaba.fastjson.JSON
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import com.konstant.konstanttools.tools.FileUtils
import com.konstant.konstanttools.ui.adapter.AdapterGridView
import kotlinx.android.synthetic.main.activity_city_manager.*
import kotlinx.android.synthetic.main.title_layout.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class CityManagerActivity : BaseActivity() {

    private val mCityList = mutableListOf<DirectData.ProvinceBean.CityBean.CountyBean>()
    private val cityList = mutableListOf<String>("当前位置")
    private val mAdapter by lazy { AdapterGridView(this, cityList) }
    private var selectedDirect: DirectData.ProvinceBean.CityBean.CountyBean =
            DirectData.ProvinceBean.CityBean.CountyBean("010101", "北京", "101010100")

    private lateinit var mPickerProvince: WheelPicker
    private lateinit var mPickerCity: WheelPicker
    private lateinit var mPickerDirect: WheelPicker

    private lateinit var mPop: PopupWindow
    private lateinit var mConfirm: Button
    private lateinit var mCancel: Button

    val directList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        setTitle("城市管理")
        initPopWindow()
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        grid_city.adapter = mAdapter
        grid_city.setOnItemLongClickListener { _, _, position, _ ->
            onItemLongClick(cityList[position])
        }
        img_more.setOnClickListener {
            if (!mPop.isShowing) {
                mPop.showAtLocation(layout_city_mamger, Gravity.BOTTOM, 0, 0)
            } else {
                mPop.dismiss()
            }
        }
        readLocalCityList()
    }

    // 长按城市块后弹窗
    private fun onItemLongClick(cityName: String): Boolean {
        AlertDialog.Builder(this@CityManagerActivity)
                .setMessage("是否要删除$cityName?")
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("确定") { dialog, _ ->
                    dialog.dismiss()
                    cityList.remove(cityName)
                    val index = mCityList.indices.lastOrNull { index -> cityName == mCityList[index].name }
                    index?.let { mCityList.removeAt(index) }
                    mAdapter.notifyDataSetChanged()
                }
                .create().show()
        return true
    }

    // 添加城市
    private fun initPopWindow() {
        val view = LayoutInflater.from(this).inflate(R.layout.pop_window_menu_weather, null)
        mPickerProvince = view.findViewById(R.id.picker_province_new) as WheelPicker
        mPickerCity = view.findViewById(R.id.picker_city_new) as WheelPicker
        mPickerDirect = view.findViewById(R.id.picker_direct_new) as WheelPicker
        mConfirm = view.findViewById(R.id.btn_confirm) as Button
        mCancel = view.findViewById(R.id.btn_cancel) as Button

        mConfirm.setOnClickListener {
            mPop.dismiss()
            saveLocalCityLost(selectedDirect)
        }
        mCancel.setOnClickListener { mPop.dismiss() }

        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        mPop.animationStyle = R.style.pop_window_anim
        mPop.isOutsideTouchable = true
        mPop.isTouchable = true
        readyDirectData()
    }

    // 初始化城市的相关数据
    private fun readyDirectData() {
        val data = readData()
        FileUtils.saveFileToInnerStorage(this, "json.txt", data.toByteArray())
        val china = JSON.parseObject(data, DirectData::class.java)
        val provinceNameList = mutableListOf<String>()
        val cityNameList = mutableListOf<String>()

        china.province.map { provinceNameList.add(it.name) }
        china.province[0].city.map { cityNameList.add(it.name) }
        china.province[0].city[0].county.map { directList.add(it.name) }

        mPickerProvince.data = provinceNameList
        mPickerCity.data = cityNameList
        mPickerDirect.data = directList

        var selectedProvince: DirectData.ProvinceBean = china.province[0] // 选中的省份
        var selectedCity: DirectData.ProvinceBean.CityBean = china.province[0].city[0] // 选中的城市
        selectedDirect = china.province[0].city[0].county[0] // 选中的地区

        provinceNameList.clear()
        china.province.map { provinceNameList.add(it.name) }
        Log.i("provinceList size", "${provinceNameList.size}")
        mPickerProvince.data = provinceNameList

        mPickerProvince.setOnItemSelectedListener { _, _, position ->

            // 保存选中的省
            selectedProvince = china.province[position]

            // 城市列表清空，并重新设置
            cityNameList.clear()
            selectedProvince.city.map { cityNameList.add(it.name) }
            mPickerCity.data = cityNameList
            mPickerCity.selectedItemPosition = 0

            // 地区列表清空，并重新设置
            directList.clear()
            selectedProvince.city[0].county.map { directList.add(it.name) }
            mPickerDirect.data = directList

            // 保存默认选中的地区
            selectedDirect = selectedProvince.city[0].county[0]
        }

        mPickerCity.setOnItemSelectedListener { _, _, position ->

            // 保存选中的城市
            selectedCity = selectedProvince.city[position]

            // 地区列表清空，并重新设置
            directList.clear()
            selectedCity.county.map { directList.add(it.name) }
            mPickerDirect.data = directList
            mPickerDirect.selectedItemPosition = 0

            // 保存默认选中的地区
            selectedDirect = selectedCity.county[0]
        }

        mPickerDirect.setOnItemSelectedListener { _, _, position ->

            // 保存选中的地区
            selectedDirect = selectedCity.county[position]
        }
    }

    // 获取城市信息列表
    private fun readData(): String {
        val inputStream = assets.open("directdata.json")
        val stream = ByteArrayOutputStream()
        val bytes = ByteArray(4096)
        var len = 0
        while (len != -1) {
            len = inputStream.read(bytes)
            if (len != -1) {
                stream.write(bytes, 0, len)
            }
        }
        return String(stream.toByteArray())

    }

    // 获取本地保存的用户手动添加的城市信息列表
    private fun readLocalCityList() {
        val bytes = FileUtils.readFileFromInnerStorage(this, "cityList.json")
        bytes?.let {
            mCityList.addAll(JSON.parseArray(String(bytes), DirectData.ProvinceBean.CityBean.CountyBean::class.java))
            cityList.clear()
            cityList.add("当前位置")
            mCityList.map { cityList.add(it.name) }
            mAdapter.notifyDataSetChanged()
        }
    }

    // 新添加的城市保存到本地
    private fun saveLocalCityLost(country: DirectData.ProvinceBean.CityBean.CountyBean) {
        val filter = mCityList.filter { it.weatherCode == country.weatherCode }
        if (filter.isEmpty()) {
            mCityList.add(country)
            cityList.clear()
            cityList.add("当前位置")
            mCityList.map { cityList.add(it.name) }
            FileUtils.saveFileToInnerStorage(this, "cityList.json", mCityList.toString().toByteArray())
            mAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "${country.name}已存在与列表中", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        FileUtils.saveFileToInnerStorage(this, "cityList.json", mCityList.toString().toByteArray())
    }

}
