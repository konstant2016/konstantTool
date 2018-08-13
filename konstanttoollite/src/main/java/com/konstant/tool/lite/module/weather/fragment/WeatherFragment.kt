package com.konstant.tool.lite.module.weather.fragment

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.server.WeatherService
import com.konstant.tool.lite.module.weather.param.TitleChanged
import com.konstant.tool.lite.module.weather.server.WeatherResponse
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherDaily
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherHourly
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import com.yanzhenjie.permission.AndPermission
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.layout_weather_15_daily.*
import kotlinx.android.synthetic.main.layout_weather_24_hour.*
import kotlinx.android.synthetic.main.layout_weather_current.*
import org.greenrobot.eventbus.EventBus
import java.text.SimpleDateFormat

/**
 * 描述:天气展示页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:22
 * 备注:
 */

class WeatherFragment : BaseFragment() {

    private val mLocationClient by lazy { AMapLocationClient(activity) }
    private var mDirectCode: String = ""

    private val mListHour = ArrayList<WeatherResponse.HourlyForecastBean>()
    private val mAdapterHour by lazy { AdapterWeatherHourly(activity, mListHour) }

    private val mListDaily = ArrayList<WeatherResponse.WeatherBean>()
    private val mAdapterDay by lazy { AdapterWeatherDaily(activity, mListDaily) }

    private var mCurrentCity = "加载中"
    private var needReLocation = false      // 是否需要再次定位
    private var isReRequest = false         // 是否为第二次请求数据

    companion object {
        private val PARAM = "param"
        fun newInstance(direct: String): BaseFragment {
            val fragment = WeatherFragment()
            val bundle = Bundle()
            bundle.putString(PARAM, direct)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val p1 = arguments.getString(PARAM)
        val p2 = CountryManager.getCityCode()

        // 如果传进来的参数为空，但是缓存参数不为空，才需要二次定位
        if (p1.isEmpty() && p2.isNotEmpty()) needReLocation = true

        mDirectCode = if (p1.isNotEmpty()) p1 else p2

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onLazyLoad() {

        recycler_weather_hour.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = mAdapterHour
        }

        recycler_weather_day.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapterDay
        }

        refresh_layout.apply {
            setHeaderView(BezierLayout(activity))
            setEnableLoadmore(false)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                    if (mDirectCode.isEmpty()) {
                        mLocationClient.startLocation()
                    } else {
                        requestData(mDirectCode)
                    }
                }
            })
        }

        requestPermission()

    }


    private fun requestPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                .onGranted {
                    initLocationClient()
                    refresh_layout.startRefresh()
                }
                .onDenied { showToast("您拒绝了定位权限")}
                .start()
    }


    // 初始化获取当前位置的相关控件
    private fun initLocationClient() {

        val option = AMapLocationClientOption()
        option.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        mLocationClient.setLocationOption(option)

        mLocationClient.setLocationListener {
            if (isDetached) return@setLocationListener
            if (it.errorCode != AMapLocation.LOCATION_SUCCESS) {
                showToast("定位失败，请稍后重试")
            } else {
                Log.d("当前位置", "${it.province},${it.city},${it.district}")
                queryWeatherCode(it.province, it.city, it.district)
            }
        }
    }

    // 分析出当前城市 天气编号
    private fun queryWeatherCode(province: String, city: String, direct: String) {
        Log.d("当前位置", "$province,$city,$direct")
        val weatherCode = CountryManager.queryWeatherCode(province, city, direct)
        if (weatherCode.isEmpty()) return
        mDirectCode = weatherCode
        CountryManager.setCityCode(mDirectCode)
        requestData(mDirectCode)
    }


    // 向服务器请求数据
    private fun requestData(location: String) {
        WeatherService.queryWeather(location) { state, data ->
            if (isReRequest){
                isReRequest = false
            }else{
                stopRefreshAnim()
            }
            if (isDetached or !state) return@queryWeather
            setData(String(data))
        }
    }

    // 设置数据
    private fun setData(data: String) {
        val result = JSON.parseObject(data, WeatherResponse::class.java)
        val realTime = result.realtime
        val hourlyForecast = result.hourly_forecast
        val weatherList = result.weather
        mListHour.addAll(hourlyForecast)
        mListDaily.addAll(weatherList)

        activity?.runOnUiThread {

            // 头部的信息
            tv_weather_direct.text = realTime.wind.direct
            tv_weather_power.text = realTime.wind.power

            tv_weather_describe.text = "天气：${realTime.weather.info}"
            tv_temperature.text = realTime.weather.temperature

            val time = SimpleDateFormat("MM-dd HH:mm").format(realTime.dataUptime.toLong() * 1000)
            tv_weather_update_time.text = "更新时间：${time}"

            // 逐小时预报
            mAdapterHour.notifyDataSetChanged()

            // 逐天预报
            mAdapterDay.notifyDataSetChanged()

            // 更换父标题
            mCurrentCity = "${result.area[0][0]} ${result.area[2][0]}"
            if (isFragmentResume()) {
                setActivityTitle(mCurrentCity)
            }
        }
        if (needReLocation) {
            mLocationClient.startLocation()
            isReRequest = true
            needReLocation = false
        }
    }

    // 设置标题
    private fun setActivityTitle(title: String) {
        EventBus.getDefault().post(TitleChanged(title))
    }

    // 停止刷新
    private fun stopRefreshAnim() {
        activity?.runOnUiThread { refresh_layout?.finishRefreshing() }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            setActivityTitle(mCurrentCity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mLocationClient.onDestroy()
    }

}