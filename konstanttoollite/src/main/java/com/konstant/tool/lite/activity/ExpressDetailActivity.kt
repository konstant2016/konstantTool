package com.konstant.tool.lite.activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.R
import com.konstant.tool.lite.adapter.AdapterExpressDetail
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.ExpressManager
import com.konstant.tool.lite.eventbusparam.ExpressChanged
import com.konstant.tool.lite.server.Service
import com.konstant.tool.lite.server.response.ExpressResponse
import com.konstant.tool.lite.view.KonstantArrayAdapter
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_express_detail.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * 描述:物流详情页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class ExpressDetailActivity : BaseActivity() {

    var mState = ""

    var mCompanyId = ""
    var mOrderNo = ""
    var mRemark = ""

    val coms by lazy { resources.getStringArray(R.array.express_company) }
    val ids by lazy { resources.getStringArray(R.array.express_company_id) }

    lateinit var mPop: PopupWindow

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
        mRemark = intent.getStringExtra("mRemark") ?: "保密物件"

        updateUI()

        ExpressManager.addExpress(mOrderNo, mCompanyId, mRemark, "暂无信息")
        sendExpressChanged()

        listview_detail.adapter = mAdapter

        btn_retry.setOnClickListener { queryExpress() }

        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { onMorePressed() }
    }

    // 开始查询物流信息
    private fun queryExpress() {
        onLoading()
        Service.expressQuery(mCompanyId, mOrderNo) { state, data ->
            if (!state) {
                onError()
                return@expressQuery
            }
            val response = JSON.parseObject(String(data), ExpressResponse::class.java)
            Log.d(this.localClassName, String(data))
            if (response.status != "200") {
                onError()
                return@expressQuery
            }

            onSuccess()
            updateData(response)

        }
    }

    // 更新界面
    private fun updateUI() {

        tv_state.text = mState

        tv_remark.text = mRemark

        var com = ""
        ids.forEachIndexed { index, _ ->
            if (mCompanyId == ids[index]) {
                com = coms[index]
            }
        }
        tv_num.text = "$com:$mOrderNo"
    }

    // 刷新数据
    private fun updateData(response: ExpressResponse) {
        runOnUiThread {
            mDatas.addAll(response.data)
            mAdapter.notifyDataSetChanged()

            when (response.state) {
                0 -> mState = "在途中"
                1 -> mState = "已揽件"
                2 -> mState = "疑难件"
                3 -> mState = "已签收"
                4 -> mState = "已退签"
                5 -> mState = "派件中"
                6 -> mState = "退回中"
            }

            updateUI()

            ExpressManager.updateExpress(mOrderNo, mCompanyId, mRemark, mState)
        }
    }

    // 正在加载中
    private fun onLoading() {
        runOnUiThread {
            layout_loading.visibility = View.VISIBLE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.GONE
        }

    }

    // 加载失败
    private fun onError() {
        runOnUiThread {
            layout_loading.visibility = View.GONE
            layout_erroe.visibility = View.VISIBLE
            listview_detail.visibility = View.GONE
        }

    }

    // 加载成功
    private fun onSuccess() {
        runOnUiThread {
            layout_loading.visibility = View.GONE
            layout_erroe.visibility = View.GONE
            listview_detail.visibility = View.VISIBLE
        }
    }

    // 右上角的更多按钮按下后
    private fun onMorePressed() {
        val view = LayoutInflater.from(this).inflate(R.layout.pop_express, null)
        view.findViewById(R.id.tv_change_order).setOnClickListener { changeOrderNo() }
        view.findViewById(R.id.tv_change_company).setOnClickListener { changeCompany() }
        view.findViewById(R.id.tv_change_remark).setOnClickListener { changeRemark() }
        view.findViewById(R.id.tv_delete).setOnClickListener { deleteOrder() }

        mPop = PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true)
        mPop.showAsDropDown(title_bar)

    }

    // 修改物流单号
    private fun changeOrderNo() {
        mPop.dismiss()
        val view = layoutInflater.inflate(R.layout.layout_input, null)
        val edit = view.findViewById(R.id.edit_input) as EditText
        edit.setText(mOrderNo)
        KonstantDialog(this)
                .setMessage("输入运单号")
                .addView(view)
                .setPositiveListener {
                    if (TextUtils.isEmpty(edit.text)) {
                        Toast.makeText(this, "记得输入运单号哦", Toast.LENGTH_SHORT).show()
                        return@setPositiveListener
                    }
                    it.dismiss()
                    ExpressManager.deleteExpress(mOrderNo)
                    mOrderNo = edit.text.toString()
                    updateUI()
                    ExpressManager.addExpress(mOrderNo, mCompanyId, mRemark, mState)
                    sendExpressChanged()
                    queryExpress()
                }
                .createDialog()
    }

    // 修改物流公司
    private fun changeCompany() {
        mPop.dismiss()
        val view = LayoutInflater.from(this).inflate(R.layout.layout_spinner, null)

        val spinner = view.findViewById(R.id.spinner_company) as Spinner
        spinner.adapter = KonstantArrayAdapter(this, R.layout.item_spinner_bg, coms)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mCompanyId = ids[position]
            }
        }

        KonstantDialog(this)
                .setMessage("选择物流公司：")
                .addView(view)
                .setPositiveListener {
                    it.dismiss()
                    updateUI()
                    queryExpress()
                    ExpressManager.updateExpress(mOrderNo, mCompanyId, null, null)
                    sendExpressChanged()
                }
                .createDialog()
    }

    // 修改备注
    private fun changeRemark() {
        mPop.dismiss()
        val view = layoutInflater.inflate(R.layout.layout_input, null)
        val edit = view.findViewById(R.id.edit_input) as EditText
        edit.setText(mRemark)
        KonstantDialog(this)
                .setMessage("输入备注")
                .addView(view)
                .setPositiveListener {
                    it.dismiss()
                    if (TextUtils.isEmpty(edit.text)) {
                        Toast.makeText(this, "记得输入备注哦", Toast.LENGTH_SHORT).show()
                        return@setPositiveListener
                    }
                    mRemark = edit.text.toString()
                    updateUI()
                    ExpressManager.updateExpress(mOrderNo, null, mRemark, null)
                    sendExpressChanged()
                }
                .createDialog()
    }

    // 删除运单
    private fun deleteOrder() {
        mPop.dismiss()
        KonstantDialog(this)
                .setMessage("确定要删除此运单号？")
                .setPositiveListener {
                    it.dismiss()
                    ExpressManager.deleteExpress(mOrderNo)
                    sendExpressChanged()
                    this.finish()
                }
                .createDialog()
    }

    // 发送物流信息变化的广播
    private fun sendExpressChanged() {
        EventBus.getDefault().post(ExpressChanged())
    }

}
