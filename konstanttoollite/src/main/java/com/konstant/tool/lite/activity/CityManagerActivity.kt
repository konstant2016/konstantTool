package com.konstant.tool.lite.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.PopupWindow
import com.aigestudio.wheelpicker.WheelPicker
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.R
import com.konstant.tool.lite.adapter.AdapterCityList
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.LocalCountryData
import com.konstant.tool.lite.data.LocalCountryManager
import com.konstant.tool.lite.eventbusparam.WeatherStateChanged
import com.konstant.tool.lite.server.other.China
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_city_manager.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream


@SuppressLint("MissingSuperCall")
class CityManagerActivity : BaseActivity() {

    private val mCityList = ArrayList<LocalCountryData>()
    private lateinit var mChina: China

    private val mAdapter by lazy { AdapterCityList(this, mCityList) }
    private var mSelectedCountry = LocalCountryData("101010100", "朝阳")

    private lateinit var mPickerProvince: WheelPicker
    private lateinit var mPickerCity: WheelPicker
    private lateinit var mPickerCountry: WheelPicker

    private lateinit var mPop: PopupWindow
    private lateinit var mConfirm: Button
    private lateinit var mCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        setTitle("城市管理")
        initBaseViews()
        initCitySelector()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE
        grid_city.adapter = mAdapter

        grid_city.setOnItemLongClickListener { _, _, position, _ ->
            onItemLongClick(mCityList[position])
        }

        grid_city.setOnItemClickListener { _, _, position, _ ->
            EventBus.getDefault().post(WeatherStateChanged(true, position))
            this.finish()
        }
        // 弹起城市选择页
        img_more.setOnClickListener {
            if (!mPop.isShowing) {
                mPop.showAtLocation(layout_city_mamger, Gravity.BOTTOM, 0, 0)
                layout_city_mamger.visibility = View.VISIBLE
            } else {
                mPop.dismiss()
            }
        }
        // 读取本地城市列表
        readLocalCityList()
    }

    // 长按城市块后弹窗
    private fun onItemLongClick(direct: LocalCountryData): Boolean {
        KonstantDialog(this)
                .setMessage("是否要删除${direct.cityName}?")
                .setPositiveListener {
                    it.dismiss()
                    if (!LocalCountryManager.deleteCity(direct)){
                        return@setPositiveListener
                    }
                    readLocalCityList()
                    EventBus.getDefault().post(WeatherStateChanged(true, 0))
                }
                .createDialog()
        return true
    }

    // 初始化城市选择器
    private fun initCitySelector() {
        // 初始化弹窗布局
        initPopWindow()
        // 准备城市列表数据
        readChinaInformation()
        // 给选择器添加数据，并设置监听
        readyPickerData()
    }

    // 初始化添加城市的弹窗
    private fun initPopWindow() {
        val view = LayoutInflater.from(this).inflate(R.layout.pop_window_menu_weather, null)
        mPickerProvince = view.findViewById(R.id.picker_province_new) as WheelPicker
        mPickerCity = view.findViewById(R.id.picker_city_new) as WheelPicker
        mPickerCountry = view.findViewById(R.id.picker_direct_new) as WheelPicker
        mConfirm = view.findViewById(R.id.btn_confirm) as Button
        mCancel = view.findViewById(R.id.btn_cancel) as Button

        mConfirm.setOnClickListener {
            mPop.dismiss()
            saveLocalCityList(mSelectedCountry)
            EventBus.getDefault().post(WeatherStateChanged(true, 0))
        }
        mCancel.setOnClickListener { mPop.dismiss() }

        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        mPop.animationStyle = R.style.popwin_anim_style
        mPop.isOutsideTouchable = true
        mPop.isTouchable = true

        mPop.setOnDismissListener { layout_city_mamger.visibility = View.GONE }

    }

    // 初始化城市的相关数据
    private fun readyPickerData() {
        // 初始化省市区的列表
        val provNames = mutableListOf<String>()
        val cityNames = mutableListOf<String>()
        val countryNames = mutableListOf<String>()

        mChina.province.map { provNames.add(it.name) }
        mChina.province[0].city.map { cityNames.add(it.name) }
        mChina.province[0].city[0].county.map { countryNames.add(it.name) }

        mPickerProvince.data = provNames
        mPickerCity.data = cityNames
        mPickerCountry.data = countryNames

        var selectedProvince: China.ProvinceBean = mChina.province[0] // 默认选中的省
        var selectedCity: China.ProvinceBean.CityBean = mChina.province[0].city[0] // 默认选中的市
        var country = mChina.province[0].city[0].county[0]
        mSelectedCountry = LocalCountryData(country.weatherCode, country.name) // 默认选中的区

        mPickerProvince.setOnItemSelectedListener { _, _, position ->
            // 选中的省变化后，市列表和区列表都需要重新设置

            // 保存选中的省
            selectedProvince = mChina.province[position]

            // 市列表清空，重新添加市列表，并默认选中第一位
            cityNames.clear()
            selectedProvince.city.map { cityNames.add(it.name) }
            mPickerCity.data = cityNames
            mPickerCity.selectedItemPosition = 0

            // 地区列表清空，并重新添加区列表，并默认选中第一位
            countryNames.clear()
            selectedProvince.city[0].county.map { countryNames.add(it.name) }
            mPickerCountry.data = countryNames
            mPickerCountry.selectedItemPosition = 0

            // 保存默认选中的地区
            country = selectedProvince.city[0].county[0]
            mSelectedCountry = LocalCountryData(country.weatherCode, country.name)
        }

        mPickerCity.setOnItemSelectedListener { _, _, position ->

            // 保存选中的市
            selectedCity = selectedProvince.city[position]

            // 地区列表清空，并重新添加区列表，并默认选中第一位
            countryNames.clear()
            selectedCity.county.map { countryNames.add(it.name) }
            mPickerCountry.data = countryNames
            mPickerCountry.selectedItemPosition = 0

            // 保存默认选中的区
            country = selectedCity.county[0]
            mSelectedCountry = LocalCountryData(country.weatherCode, country.name)
        }

        mPickerCountry.setOnItemSelectedListener { _, _, position ->

            // 保存选中的地区
            country = selectedCity.county[position]
            mSelectedCountry = LocalCountryData(country.weatherCode, country.name)
        }
    }

    // 读取城市信息列表
    private fun readChinaInformation() {
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
        val data = String(stream.toByteArray())
        mChina = JSON.parseObject(data, China::class.java)
    }

    // 获取本地保存的用户手动添加的城市信息列表
    private fun readLocalCityList() {
        val list = LocalCountryManager.readCityList()
        mCityList.clear()
        mCityList.add(LocalCountryData("", "当前位置"))
        mCityList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }

    // 新添加的城市保存到本地
    private fun saveLocalCityList(city: LocalCountryData) {
        LocalCountryManager.addCity(city.cityCode, city.cityName)
        readLocalCityList()
    }

    override fun onDestroy() {
        LocalCountryManager.onDestroy(this)
        super.onDestroy()
    }
}
