package com.konstant.konstanttools.ui.activity.toolactivity.weather

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseFragment
import com.konstant.konstanttools.server.Service
import com.konstant.konstanttools.server.response.WeatherResponse
import com.konstant.konstanttools.util.KeyConstant
import com.konstant.konstanttools.util.UrlConstant
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.weather_listview_headview.*

/**
 * 描述:天气信息页面的fragment
 * 创建人:菜籽
 * 创建时间:2018/1/3 下午5:48
 * 备注:
 */

class WeatherFragment : BaseFragment() {

    private val list by lazy { mutableListOf<WeatherResponse.HeWeather.DailyForecast>() }
    private val adapter: AdapterWeather by lazy { AdapterWeather(mActivity, list) }
    private lateinit var mLocationClient: AMapLocationClient

    private var mDistrict = ""

    companion object {
        private val PARAM = "param"
        fun newInstance(direct: String) : Fragment {
            val fragment = WeatherFragment()
            val bundle = Bundle()
            bundle.putString(PARAM, direct)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDistrict = arguments.getString(PARAM)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather_list_view.adapter = adapter
        addHeaderView()
        swipe_layout.isRefreshing = true
        swipe_layout.setOnRefreshListener { requestData(mDistrict) }
        if (mDistrict.isNullOrEmpty()) {
            initLocationClient()
            requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, "需要获取定位权限来确定您当前的位置")
        } else {
            requestData(mDistrict)
        }
    }


    override fun onPermissionResult(result: Boolean) {
        super.onPermissionResult(result)
        if (result) {
            mLocationClient.startLocation()
        } else {
            Toast.makeText(mActivity, "您拒绝了定位权限", Toast.LENGTH_SHORT).show()
        }
    }

    // listview添加头布局
    private fun addHeaderView() {
        val headerView = LayoutInflater.from(mActivity).inflate(R.layout.weather_listview_headview, null)
        weather_list_view.addHeaderView(headerView)
    }

    // 初始化获取当前位置的相关控件
    private fun initLocationClient() {
        mLocationClient = AMapLocationClient(mActivity)
        val option = AMapLocationClientOption()
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationClient.setLocationOption(option)

        mLocationClient.setLocationListener {
            if (it.errorCode != AMapLocation.LOCATION_SUCCESS) {
                Toast.makeText(mActivity, "定位失败，请稍后重试", Toast.LENGTH_SHORT).show()
            } else {
                mDistrict = "${it.latitude},${it.longitude}"
                requestData(mDistrict)
            }
        }
    }

    // 向服务器请求数据
    private fun requestData(location: String) {
        Service.queryWeather(UrlConstant.WEATHER_URL, location, KeyConstant.WEATHER_KEY) { state, data ->
            if (mActivity.isDestroyed) return@queryWeather
            stopRefreshAnim()
            if (!state) return@queryWeather
            val response = JSON.parseObject(data, WeatherResponse::class.java)
            if (response.heWeather6[0].status != "ok") return@queryWeather
            updateViews(response.heWeather6[0])
        }
    }

    // 停止刷新
    private fun stopRefreshAnim() {
        mActivity.runOnUiThread { swipe_layout?.isRefreshing = false }
    }

    // 更新界面
    private fun updateViews(data: WeatherResponse.HeWeather) {
        if(this.isDetached) return
        mActivity.runOnUiThread {
            list.clear()
            list.addAll(data.daily_forecast)
            adapter.notifyDataSetChanged()
            tv_location.text = "省份:${data.basic.admin_area}  市级:${data.basic.parent_city}  地区:${data.basic.location}"
            tv_latlng.text = "经度:${data.basic.lat}  纬度:${data.basic.lon}"
            tv_update_time.text = "更新时间：${data.update.loc}"
        }
    }
}