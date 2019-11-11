package com.konstant.tool.lite.module.weather.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.H5Activity
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherDaily
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherHourly
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.network.response.WeatherResponse
import com.konstant.tool.lite.util.PermissionRequester
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.layout_weather_days.*
import kotlinx.android.synthetic.main.layout_weather_hours.*
import kotlinx.android.synthetic.main.layout_weather_current.*
import java.text.SimpleDateFormat

/**
 * 描述:天气展示页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:22
 * 备注:
 */

class WeatherFragment : BaseFragment() {

    private val mPresenter by lazy { WeatherPresenter(getNotNullContext(), mDisposable) }

    private val mListHour = ArrayList<WeatherResponse.HourlyForecastBean>()
    private val mAdapterHour by lazy { AdapterWeatherHourly(getNotNullContext(), mListHour) }

    private val mListDaily = ArrayList<WeatherResponse.WeatherBean>()
    private val mAdapterDay by lazy { AdapterWeatherDaily(mListDaily) }

    private var mCurrentCity = KonApplication.context.getString(R.string.base_loading)

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onLazyLoad() {

        recycler_weather_hour.apply {
            layoutManager = LinearLayoutManager(getNotNullContext(), RecyclerView.HORIZONTAL, false)
            adapter = mAdapterHour
        }

        recycler_weather_day.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(getNotNullContext())
            adapter = mAdapterDay
        }

        refresh_layout.apply {
            setHeaderView(BezierLayout(getNotNullContext()))
            setEnableLoadmore(false)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                    requestWeather()
                }
            })
        }

        requestPermission()
    }


    private fun requestPermission() {
        val permissions = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        PermissionRequester.requestPermission(getNotNullContext(), permissions, {
            refresh_layout.startRefresh()
        }, {
            showToast(getString(R.string.weather_permission_cancel))
        })
    }

    // 请求天气数据
    private fun requestWeather() {
        val directCode = arguments?.getString(PARAM)
        if (TextUtils.isEmpty(directCode)) {
            requestLocationWeather()
        } else {
            requestWeatherWithCode(directCode!!)
        }
    }

    // 请求当前城市数据
    private fun requestLocationWeather() {
        refresh_layout.startRefresh()
        mPresenter.getCurrentLocationWeather(object : WeatherPresenter.WeatherResult {
            override fun onSuccess(response: WeatherResponse, directCode: String) {
                stopRefreshAnim()
                updateUI(response)
                CountryManager.setCityCode(directCode)
            }

            override fun onLocationError() {
                val code = CountryManager.getCityCode()
                if (code.isEmpty()) {
                    stopRefreshAnim()
                    showToast(getString(R.string.weather_location_fail_01))
                    return
                }
                showToast(getString(R.string.weather_location_fail_02))
                requestWeatherWithCode(code)
            }

            override fun onWeatherError() {
                stopRefreshAnim()
                showToast(getString(R.string.weather_date_error))
            }
        })
    }

    // 请求指定城市的数据
    private fun requestWeatherWithCode(directCode: String) {
        mPresenter.getWeatherWithCode(directCode) {
            stopRefreshAnim()
            if (it.weather == null) {
                showToast(getString(R.string.weather_date_error))
            } else {
                updateUI(it)
            }
        }
    }

    // 设置数据
    @SuppressLint("SetTextI18n")
    private fun updateUI(result: WeatherResponse) {
        val realTime = result.realtime
        val hourlyForecast = result.hourly_forecast
        val weatherList = result.weather
        val alert = result.alert
        mListHour.addAll(hourlyForecast)
        mListDaily.addAll(weatherList)

        activity?.runOnUiThread {

            // 头部的信息
            tv_weather_direct.text = realTime.wind.direct
            tv_weather_power.text = realTime.wind.power

            tv_weather_describe.text = "当前：${realTime.weather.info}"
            tv_temperature.text = realTime.weather.temperature

            val time = SimpleDateFormat("MM-dd HH:mm").format(realTime.dataUptime.toLong() * 1000)
            tv_weather_update_time.text = "更新时间：$time"

            if (alert != null && alert.size > 0) {
                tv_weather_update_time.text = alert[0].content
                tv_weather_update_time.setOnClickListener {
                    val intent = Intent(activity, H5Activity::class.java)
                    intent.putExtra(H5Activity.H5_URL, alert[0].originUrl)
                    startActivity(intent)
                }
            }

            // 逐小时预报
            mAdapterHour.notifyDataSetChanged()

            // 逐天预报
            mAdapterDay.notifyDataSetChanged()

            // 更换父标题
            val province = result.area[0][0]
            val direct = result.area[2][0]
            val des = if (province == direct) province else "${result.area[0][0]} ${result.area[2][0]}"
            mCurrentCity = des
            if (isResumed) setTitle(mCurrentCity)
        }
    }

    // 停止刷新
    private fun stopRefreshAnim() {
        activity?.runOnUiThread { refresh_layout?.finishRefreshing() }
    }

    override fun onResume() {
        super.onResume()
        setTitle(mCurrentCity)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }
}