package com.konstant.tool.lite.module.weather.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.weather.adapter.AdapterCityList
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.data.LocalCountry
import com.konstant.tool.lite.base.WeatherStateChanged
import com.konstant.tool.lite.view.CityPickerView
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_city_manager.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus


@SuppressLint("MissingSuperCall")
class CityManagerActivity : BaseActivity() {

    private val mCityList = ArrayList<LocalCountry>()
    private val mAdapter by lazy { AdapterCityList(this, mCityList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_manager)
        setTitle("城市管理")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 弹起城市选择页
        img_more.apply {
            visibility = View.VISIBLE
            setOnClickListener { onMoreClick() }
        }

        grid_city.apply {
            adapter = mAdapter

            setOnItemLongClickListener { _, _, position, _ ->
                if (position == 0) return@setOnItemLongClickListener true
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
                .setMessage("是否要删除${direct.directName}?")
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

    private fun onMoreClick(){
        val dialog = KonstantDialog(this)
        val view = CityPickerView(this).apply {
            setOnCancelClick { dialog.dismiss() }
            setOnConfirmClick {
                dialog.dismiss()
                saveLocalCityList(LocalCountry(it.weatherCode, it.name))
                EventBus.getDefault().post(WeatherStateChanged(true, 0))
            }
        }
        dialog.addView(view).hideNavigation().createDialog()
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
