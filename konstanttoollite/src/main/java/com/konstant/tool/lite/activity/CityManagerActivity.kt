package com.konstant.tool.lite.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.aigestudio.wheelpicker.WheelPicker
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.R
import com.konstant.tool.lite.adapter.AdapterCityList
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.LocalCountry
import com.konstant.tool.lite.data.CountryManager
import com.konstant.tool.lite.eventbusparam.WeatherStateChanged
import com.konstant.tool.lite.server.other.China
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_city_manager.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus


@SuppressLint("MissingSuperCall")
class CityManagerActivity : BaseActivity() {

    private val mCityList = ArrayList<LocalCountry>()

    private val mAdapter by lazy { AdapterCityList(this, mCityList) }
    private lateinit var mSelectedCountry:China.Province.City.County

    private lateinit var mPop: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        setTitle("城市管理")
        initBaseViews()
        initCitySelector()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 弹起城市选择页
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (!mPop.isShowing) {
                    mPop.showAtLocation(layout_city_mamger, Gravity.BOTTOM, 0, 0)
                    layout_city_mamger.visibility = View.VISIBLE
                } else {
                    mPop.dismiss()
                }
            }
        }

        grid_city.apply {
            adapter = mAdapter

            setOnItemLongClickListener { _, _, position, _ ->
                onItemLongClick(mCityList[position])
            }

            setOnItemClickListener { _, _, position, _ ->
                EventBus.getDefault().post(WeatherStateChanged(false, position))
                this@CityManagerActivity.finish()
            }
        }

        // 读取本地城市列表
        readLocalCityList()
    }

    // 长按城市块后弹窗
    private fun onItemLongClick(direct: LocalCountry): Boolean {
        KonstantDialog(this)
                .setMessage("是否要删除${direct.cityName}?")
                .setPositiveListener {
                    it.dismiss()
                    mCityList.remove(direct)
                    CountryManager.deleteCity(direct)
                    mAdapter.notifyDataSetChanged()
                    EventBus.getDefault().post(WeatherStateChanged(true, 0))
                }
                .createDialog()
        return true
    }

    // 初始化城市选择器
    private fun initCitySelector() {
        // 准备布局
        val view = LayoutInflater.from(this).inflate(R.layout.pop_window_menu_weather, null)
        val pickPro = view.findViewById(R.id.picker_province_new) as WheelPicker
        val pickCity = view.findViewById(R.id.picker_city_new) as WheelPicker
        val pickCou = view.findViewById(R.id.picker_direct_new) as WheelPicker

        // 初始化弹窗
        initPopWindow(view)

        // 准备城市列表数据
        val data = assets.open("directdata.json").bufferedReader().readText()
        val china = JSON.parseObject(data, China::class.java)

        // 设置数据，添加监听
        readyPickerData(china, pickPro, pickCity, pickCou)
    }

    // 初始化添加城市的弹窗
    private fun initPopWindow(view: View) {

        // 确认按钮
        view.findViewById(R.id.btn_confirm).setOnClickListener {
            mPop.dismiss()
            saveLocalCityList(LocalCountry(mSelectedCountry.weatherCode, mSelectedCountry.name))
            EventBus.getDefault().post(WeatherStateChanged(true, 0))
        }

        // 取消按钮
        view.findViewById(R.id.btn_cancel).setOnClickListener {
            mPop.dismiss()
        }

        // 弹窗布局
        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                .apply {
                    animationStyle = R.style.popwin_anim_style
                    isOutsideTouchable = true
                    isTouchable = true
                    setOnDismissListener { layout_city_mamger.visibility = View.GONE }
                }
    }


    // 初始化城市的相关数据
    private fun readyPickerData(china: China, pickPro: WheelPicker, pickCity: WheelPicker, pickCou: WheelPicker) {
        // 初始化省市区的列表
        pickPro.data = china.provinceList.map { it.name }
        pickCity.data = china.provinceList[0].cityList.map { it.name }
        pickCou.data = china.provinceList[0].cityList[0].countyList.map { it.name }

        var selectedProvince = china.provinceList[0] // 默认选中的省
        var selectedCity  = selectedProvince.cityList[0] // 默认选中的市
        mSelectedCountry =  selectedCity.countyList[0]// 默认选中的区

        // 选中的省变化后，市列表和区列表都需要重新设置
        pickPro.setOnItemSelectedListener { _, _, position ->

            // 保存选中的省
            selectedProvince = china.provinceList[position]

            // 市列表清空，重新添加市列表，并默认选中第一位
            pickCity.apply {
                data = selectedProvince.cityList.map { it.name }
                selectedItemPosition = 0
            }

            // 地区列表清空，并重新添加区列表，并默认选中第一位
            pickCou.apply {
                data = selectedProvince.cityList[0].countyList.map { it.name }
                selectedItemPosition = 0
            }

            // 保存默认选中的地区
            mSelectedCountry = selectedProvince.cityList[0].countyList[0]
        }

        // 选中的市发生变化后，需要重新设置区列表，并默认选中第一个
        pickCity.setOnItemSelectedListener { _, _, position ->

            // 保存选中的市
            selectedCity = selectedProvince.cityList[position]

            // 地区列表清空，并重新添加区列表，并默认选中第一位
            pickCou.apply {
                data = selectedCity.countyList.map { it.name }
                selectedItemPosition = 0
            }

            // 保存默认选中的区
            mSelectedCountry = selectedCity.countyList[0]
        }

        // 选中的区发生变化后，把选中的区保存下来
        pickCou.setOnItemSelectedListener { _, _, position ->

            // 保存选中的地区
            mSelectedCountry = selectedCity.countyList[position]
        }
    }


    // 获取本地保存的用户手动添加的城市信息列表
    private fun readLocalCityList() {
        mCityList.apply {
            clear()
            add(LocalCountry("", "当前位置"))
            addAll(CountryManager.readLocalCityList())
            mAdapter.notifyDataSetChanged()
        }
    }

    // 新添加的城市保存到本地
    private fun saveLocalCityList(country: LocalCountry) {
        CountryManager.addCity(country)
        readLocalCityList()
    }

    override fun onDestroy() {
        CountryManager.onDestroy(this)
        super.onDestroy()
    }
}
