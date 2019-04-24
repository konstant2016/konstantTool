package com.konstant.tool.lite.module.weather.fragment

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherDaily
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherHourly
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.module.weather.param.TitleChanged
import com.konstant.tool.lite.module.weather.server.WeatherResponse
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

    private val mPresenter by lazy { WeatherPresenter() }

    private var mDirectCode: String = ""

    private val mListHour = ArrayList<WeatherResponse.HourlyForecastBean>()
    private val mAdapterHour by lazy { AdapterWeatherHourly(activity as Context, mListHour) }

    private val mListDaily = ArrayList<WeatherResponse.WeatherBean>()
    private val mAdapterDay by lazy { AdapterWeatherDaily(activity as Context, mListDaily) }

    private var mCurrentCity = "加载中"

    companion object {
        private val PARAM = "directCode"
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
        val directCode = arguments?.getString(PARAM)
        directCode?.let { mDirectCode = it }
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
                    requestWeather()
                }
            })
        }

        requestPermission()
    }


    private fun requestPermission() {
        AndPermission.with(this)
                .permission(Manifest.permission.ACCESS_FINE_LOCATION)
                .onGranted {
                    refresh_layout.startRefresh()
                }
                .onDenied { showToast("您拒绝了定位权限") }
                .start()
    }

    // 请求天气数据
    private fun requestWeather(){
        if (mDirectCode.isEmpty()) {
            requestLocationWeather()
        } else {
            requestWeatherWithCode(mDirectCode)
        }
    }

    // 请求当前城市数据
    private fun requestLocationWeather() {
        refresh_layout.startRefresh()
        mPresenter.getCurrentLocationWeather { weather, directCode ->
            stopRefreshAnim()
            if (weather.isSuccess) {
                updateUI(weather)
                CountryManager.setCityCode(directCode)
            } else {
                showToast("天气信息请求失败")
            }
        }
    }

    // 请求指定城市的数据
    private fun requestWeatherWithCode(directCode: String) {
        mPresenter.getWeatherWithCode(directCode) {
            stopRefreshAnim()
            if (it.isSuccess) {
                updateUI(it)
            } else {
                showToast("天气信息请求失败")
            }
        }
    }

    // 设置数据
    private fun updateUI(result: WeatherResponse) {
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
}