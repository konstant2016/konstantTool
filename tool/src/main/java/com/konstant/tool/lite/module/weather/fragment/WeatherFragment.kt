package com.konstant.tool.lite.module.weather.fragment

import android.Manifest
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.module.weather.adapter.AdapterWeatherFragment
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.util.PermissionRequester
import com.konstant.tool.lite.view.KonstantDialog
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.lcodecore.tkrefreshlayout.header.bezierlayout.BezierLayout
import kotlinx.android.synthetic.main.fragment_weather.*
import java.lang.StringBuilder

/**
 * 描述:天气展示页
 * 创建人:菜籽
 * 创建时间:2018/4/6 下午3:22
 * 备注:
 */

class WeatherFragment : BaseFragment() {

    private val mPresenter by lazy { WeatherPresenter(getNotNullContext(), mDisposable) }
    private val mWeatherList = ArrayList<Any>()
    private val mAdapter by lazy { AdapterWeatherFragment(mWeatherList) }

    private var mCurrentCity = KonApplication.context.getString(R.string.base_loading)

    companion object {
        private const val PARAM = "directCode"
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
        refresh_layout.apply {
            setHeaderView(BezierLayout(getNotNullContext()))
            setEnableLoadmore(false)
            setOnRefreshListener(object : RefreshListenerAdapter() {
                override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                    requestWeather()
                }
            })
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(getNotNullContext(), LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        mAdapter.setOnAlertClick {
            val content = StringBuilder()
            it.forEachIndexed { index, alert ->
                content.append("${alert.time}  ${alert.title}: \n${alert.content}")
                if (index != it.size - 1) {
                    content.append("\n\n")
                }
            }
            KonstantDialog(requireContext())
                    .setTitle("天气预警")
                    .setMessage(content.toString(), Gravity.LEFT)
                    .setPositiveListener { dialog -> dialog.dismiss() }
                    .createDialog()
        }

        requestPermission()
    }


    private fun requestPermission() {
        val permissions = mutableListOf(Manifest.permission.ACCESS_FINE_LOCATION)
        PermissionRequester.requestPermission(getNotNullContext(), permissions, {
            refresh_layout?.startRefresh()
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
            override fun onSuccess(weatherList: List<Any>, cityName: String, directCode: String) {
                stopRefreshAnim()
                updateUI(weatherList, cityName)
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
        mPresenter.getWeatherWithCode(directCode, object : WeatherPresenter.WeatherResult {
            override fun onSuccess(weatherList: List<Any>, cityName: String, directCode: String) {
                stopRefreshAnim()
                updateUI(weatherList, cityName)
            }

            override fun onLocationError() {
                stopRefreshAnim()
                showToast(getString(R.string.weather_location_fail_01))
            }

            override fun onWeatherError() {
                stopRefreshAnim()
                showToast(getString(R.string.weather_date_error))
            }
        })
    }

    // 设置数据
    private fun updateUI(weatherList: List<Any>, cityName: String) {
        mWeatherList.clear()
        mWeatherList.addAll(weatherList)
        mAdapter.notifyDataSetChanged()
        mCurrentCity = cityName
        if (isResumed) setTitle(mCurrentCity)
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
