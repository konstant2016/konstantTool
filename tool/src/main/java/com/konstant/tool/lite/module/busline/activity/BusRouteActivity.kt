package com.konstant.tool.lite.module.busline.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.text.TextUtils
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.BaseFragmentAdapter
import com.konstant.tool.lite.data.AreaManager
import com.konstant.tool.lite.module.busline.data.QueryHistoryManager
import com.konstant.tool.lite.module.busline.data.QueryWrapper
import com.konstant.tool.lite.module.busline.fragment.DetailFragment
import com.konstant.tool.lite.module.busline.fragment.HistoryFragment
import com.konstant.tool.lite.module.busline.fragment.ResultFragment
import com.konstant.tool.lite.module.busline.param.BusLineResult
import com.konstant.tool.lite.view.KonstantArrayAdapter
import kotlinx.android.synthetic.main.activity_bus_route.*
import org.greenrobot.eventbus.Subscribe


class BusRouteActivity : BaseActivity() {

    private val TAG = "BusRouteActivity"

    private val mCountryNameList by lazy { AreaManager.getAreaList().map { it.name } }
    private val mAdapterAutoCity by lazy { KonstantArrayAdapter(this, mCountryNameList) }

    private val mDetailFragment = DetailFragment.newInstance()
    private val mResultFragment = ResultFragment.newInstance()
    private val mHistoryFragment = HistoryFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_route)
        setTitle("公交线路查询")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        layout_root.setOnClickListener { hideSoftKeyboard() }

        auto_tv_city.apply {
            setAdapter(mAdapterAutoCity)
        }

        img_city_close.setOnClickListener { auto_tv_city.setText("") }

        img_line_close.setOnClickListener { et_bus.setText("") }

        btn_search.setOnClickListener {
            if (TextUtils.isEmpty(et_bus.text)) {
                showToast("记得输入公交路线哦~")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(auto_tv_city.text)) {
                showToast("记得选择城市哦~")
                return@setOnClickListener
            }
            hideSoftKeyboard()
            view_pager.currentItem = 1
            QueryHistoryManager.addQueryHistory(QueryWrapper(auto_tv_city.text.toString(),et_bus.text.toString()))
            mResultFragment.queryBusLine(et_bus.text.toString(), auto_tv_city.text.toString())
        }

        view_pager.apply {
            offscreenPageLimit = 3
            adapter = BaseFragmentAdapter(supportFragmentManager,
                    with(mutableListOf<BaseFragment>()) {
                        add(mHistoryFragment)
                        add(mResultFragment)
                        add(mDetailFragment)
                        this
                    }, listOf("查询历史", "匹配结果", "路线详情"))
        }

        tab_layout.apply {
            setupWithViewPager(view_pager) }
    }

    @Subscribe
    fun onMainThread(result:BusLineResult){
        view_pager.currentItem = result.index
        mDetailFragment.setDetailData(result.data)
    }

    @Subscribe
    fun onMainThread(result:QueryWrapper){
        view_pager.currentItem = 1
        mResultFragment.queryBusLine(result.route, result.cityName)
    }

    override fun onDestroy() {
        super.onDestroy()
        QueryHistoryManager.onDestroy(this)
    }

}
