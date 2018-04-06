package com.konstant.toollite.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import com.aigestudio.wheelpicker.WheelPicker
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterCityList
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.data.LocalDirectData
import com.konstant.toollite.data.LocalDirectManager
import com.konstant.toollite.eventbusparam.IndexChanged
import kotlinx.android.synthetic.main.activity_city_manager.*
import kotlinx.android.synthetic.main.title_layout.*
import java.io.ByteArrayOutputStream
import com.konstant.toollite.server.other.DirectData
import com.konstant.toollite.view.KonstantConfirmtDialog
import org.greenrobot.eventbus.EventBus


@SuppressLint("MissingSuperCall")
class CityManagerActivity : BaseActivity() {

    private val mCityList = ArrayList<LocalDirectData>()

    private val mAdapter by lazy { AdapterCityList(this, mCityList) }
    private var mSelectedDirect = LocalDirectData("101010100", "朝阳")

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
            onItemLongClick(mCityList[position])
        }

        grid_city.setOnItemClickListener { _, _, position, _ ->
            EventBus.getDefault().post(IndexChanged(position))
            this.finish()
        }
        // 弹起城市选择页
        img_more.setOnClickListener {
            if (!mPop.isShowing) {
                backgroundAlpha(this, 0.85f)
                mPop.showAtLocation(layout_city_mamger, Gravity.BOTTOM, 0, 0)
            } else {
                mPop.dismiss()
            }
        }
        // 读取本地城市列表
        readLocalCityList()
    }

    // 长按城市块后弹窗
    private fun onItemLongClick(direct: LocalDirectData): Boolean {
        KonstantConfirmtDialog(this)
                .setMessage("是否要删除${direct.cityName}?")
                .setPositiveListener {
                    it.dismiss()
                    mCityList.remove(direct)
                    LocalDirectManager.deleteCity(this, direct)
                    mAdapter.notifyDataSetChanged()
                }
                .setNegativeListener { }
                .show()
        return true
    }

    // 初始化添加城市的弹窗
    private fun initPopWindow() {
        val view = LayoutInflater.from(this).inflate(R.layout.pop_window_menu_weather, null)
        mPickerProvince = view.findViewById(R.id.picker_province_new) as WheelPicker
        mPickerCity = view.findViewById(R.id.picker_city_new) as WheelPicker
        mPickerDirect = view.findViewById(R.id.picker_direct_new) as WheelPicker
        mConfirm = view.findViewById(R.id.btn_confirm) as Button
        mCancel = view.findViewById(R.id.btn_cancel) as Button

        mConfirm.setOnClickListener {
            mPop.dismiss()
            saveLocalCityLost(mSelectedDirect)
        }
        mCancel.setOnClickListener { mPop.dismiss() }

        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        mPop.animationStyle = R.style.popwin_anim_style
        mPop.isOutsideTouchable = true
        mPop.isTouchable = true

        mPop.setOnDismissListener {
            backgroundAlpha(this, 1.0f)
        }

        readyDirectData()
    }

    // 初始化城市的相关数据
    private fun readyDirectData() {
        val data = readCityJSON()
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
        val county = china.province[0].city[0].county[0]
        mSelectedDirect = LocalDirectData(county.weatherCode, county.name) // 选中的地区

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
            val county = selectedProvince.city[0].county[0]
            mSelectedDirect = LocalDirectData(county.weatherCode, county.name)
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
            val county  = selectedCity.county[0]
            mSelectedDirect = LocalDirectData(county.weatherCode, county.name)
        }

        mPickerDirect.setOnItemSelectedListener { _, _, position ->

            // 保存选中的地区
            val county  =  selectedCity.county[position]
            mSelectedDirect = LocalDirectData(county.weatherCode, county.name)
        }
    }

    // 读取城市信息列表JSON
    private fun readCityJSON(): String {
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
        val list = LocalDirectManager.readCityList(this)
        mCityList.clear()
        mCityList.add(LocalDirectData("","当前位置"))
        mCityList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    // 新添加的城市保存到本地
    private fun saveLocalCityLost(city: LocalDirectData) {
        LocalDirectManager.addCity(this, city.cityCode, city.cityName)
        mCityList.forEach {
            if (it.cityCode == city.cityCode)
                return
        }
        mCityList.add(city)
        mAdapter.notifyDataSetChanged()
    }

    private fun backgroundAlpha(context: Activity, bgAlpha: Float) {
        val lp = context.window.attributes
        lp.alpha = bgAlpha //0.0-1.0
        context.window.attributes = lp
        context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

}
