package com.konstant.toollite.activity

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterExpressDetail
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.server.Service
import com.konstant.toollite.server.other.ExpressData
import com.konstant.toollite.server.response.ExpressResponse
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import kotlinx.android.synthetic.main.activity_express_detail.*

class ExpressDetailActivity : BaseActivity() {

    var mState = ""

    var mCompanyId = ""
    var mOrderNo = ""
    var mRemark = "保密物件"

    val mDatas = ArrayList<ExpressResponse.DataBean>()
    val mAdapter by lazy { AdapterExpressDetail(this, mDatas) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_detail)

        setTitle("物流详情")

        initBaseViews()

        queryExpress()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        mCompanyId = intent.getStringExtra("mCompanyId")
        mOrderNo = intent.getStringExtra("mOrderNo")
        val mark = intent.getStringExtra("remark")
        if (mark != null) mRemark = mark

        listview_detail.adapter = mAdapter
    }

    fun queryExpress() {
        Service.expressQuery(mCompanyId, mOrderNo) { state, data ->
            changeLoadingState(false)
            if (!state) {
                showAlertDialog()
                return@expressQuery
            }
            val response = JSON.parseObject(data, ExpressResponse::class.java)
            if (response.status != "200") {
                showAlertDialog()
                return@expressQuery
            }

            updateData(response)

        }
    }

    // 刷新数据
    fun updateData(response: ExpressResponse) {
        runOnUiThread {
            mDatas.addAll(response.data)
            mAdapter.notifyDataSetChanged()

            var com = ""
            val comName = resources.getStringArray(R.array.express_company)
            val comID = resources.getStringArray(R.array.express_company_id)

            comID.forEachIndexed { index, s ->
                if (s == response.com) {
                    com = comName[index]
                }
            }
            tv_num.text = "$com:${response.nu}"

            when (response.state) {
                0 -> mState = "在途中"
                1 -> mState = "已揽件"
                2 -> mState = "疑难件"
                3 -> mState = "已签收"
                4 -> mState = "已退签"
                5 -> mState = "派件中"
                6 -> mState = "退回中"
            }

            tv_state.text = mState

            tv_remark.text = mRemark

            updateLocalData()
        }
    }

    // 更新本地缓存数据
    fun updateLocalData() {
        Log.d(this.localClassName, "开始更新本地物流数据")
        val s = FileUtils.readDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS)
        val list = JSON.parseArray(s, ExpressData::class.java)
        list.forEachIndexed { index, expressData ->
            if (list[index].orderNo == mOrderNo) {
                Log.d(this.localClassName, "单号相同:" + mState)
                expressData.state = mState
            }
        }
        FileUtils.saveDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS, JSON.toJSONString(list))
        Log.d(this.localClassName, "本地物流数据" + JSON.toJSONString(list))
    }

    // 隐藏加载中的界面
    fun changeLoadingState(boolean: Boolean) {
        runOnUiThread {
            if (boolean) {
                layout_loading.visibility = View.VISIBLE
                layout_detail.visibility = View.GONE
            } else {
                layout_loading.visibility = View.GONE
                layout_detail.visibility = View.VISIBLE
            }
        }
    }

    // 展示重新加载的弹窗
    fun showAlertDialog() {
        runOnUiThread {
            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("查询失败，是否重试？")
                    .setPositiveButton("确定") { dialog, _ ->
                        changeLoadingState(true)
                        queryExpress()
                        dialog.dismiss()
                    }
                    .setNegativeButton("取消") { dialog, _ ->
                        this.finish()
                    }
                    .create().show()
        }
    }
}
