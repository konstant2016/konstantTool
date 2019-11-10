package com.konstant.tool.lite.module.express.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.ExpressChanged
import com.konstant.tool.lite.data.bean.express.ExpressData
import com.konstant.tool.lite.module.express.ExpressManager
import com.konstant.tool.lite.module.express.adapter.AdapterExpressDetail
import com.konstant.tool.lite.view.KonstantDialog
import com.konstant.tool.lite.view.KonstantPopupWindow
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_express_detail.*
import kotlinx.android.synthetic.main.activity_express_detail.view.*
import kotlinx.android.synthetic.main.layout_dialog_input.view.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus
import java.util.*

/**
 * 描述:物流详情页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:10
 * 备注:
 */

class ExpressDetailActivity : BaseActivity() {

    var mState: String? = ""
    var mNumber = ""
    var mCompany: String? = ""
    var mName: String? = ""

    private val mList = ArrayList<ExpressData.Message>()
    private val mAdapter by lazy { AdapterExpressDetail(this, mList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express_detail)
        setTitle(getString(R.string.express_detail))
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        mNumber = intent.getStringExtra("number")
        mName = intent.getStringExtra("name") ?: getString(R.string.express_name_unknown)
        updateStatus()
        with(layout_recycler) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@ExpressDetailActivity, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

        btn_retry.setOnClickListener { queryExpress(mNumber) }
        ExpressManager.addExpress(mNumber)
        sendExpressChanged()
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { onMorePressed() }
        queryExpress(mNumber)
    }

    // 开始查询物流信息
    private fun queryExpress(number: String) {
        onLoading()
        ExpressPresenter(mDisposable).getExpressDetail(number, object : ExpressPresenter.ExpressResult {
            override fun onSuccess(response: ExpressData) {
                this@ExpressDetailActivity.onSuccess(response)
            }

            override fun onError() {
                this@ExpressDetailActivity.onError()
            }
        })
    }

    // 更新状态
    private fun updateStatus() {
        tv_state_express.text = mState
        tv_name.text = mName
        tv_describe.text = "$mCompany：$mNumber"
    }

    // 正在加载中
    private fun onLoading() {
        runOnUiThread {
            showLoading(true)
            base_content.layout_error.visibility = View.GONE
            base_content.layout_success.visibility = View.GONE
        }
    }

    // 加载失败
    private fun onError() {
        runOnUiThread {
            showLoading(false)
            base_content.layout_error.visibility = View.VISIBLE
            base_content.layout_success.visibility = View.GONE
        }
    }

    // 加载成功
    private fun onSuccess(response: ExpressData) {
        showLoading(false)
        mCompany = response.company
        mState = response.status
        ExpressManager.updateExpress(mNumber, mCompany, mName, mState)
        sendExpressChanged()
        runOnUiThread {
            base_content.layout_error.visibility = View.GONE
            base_content.layout_success.visibility = View.VISIBLE
            tv_describe.text = "${response.company}:${response.number}"
            updateStatus()
            mList.clear()
            mList.addAll(response.messages)
            mAdapter.notifyDataSetChanged()
        }
    }

    // 右上角的更多按钮按下后
    private fun onMorePressed() {
        KonstantPopupWindow(this)
                .setItemList(listOf(getString(R.string.express_rename_order_number), getString(R.string.express_rename_remark), getString(R.string.express_delete)))
                .setOnItemClickListener {
                    when (it) {
                        0 -> {
                            changeOrderNo()
                        }
                        1 -> {
                            changeRemark()
                        }
                        2 -> {
                            deleteOrder()
                        }
                    }
                }
                .showAsDropDown(title_bar)
    }

    // 修改物流单号
    private fun changeOrderNo() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        view.edit_input.setText(mNumber)
        view.edit_input.selectAll()
        KonstantDialog(this)
                .setMessage(getString(R.string.express_input_order_number))
                .addView(view)
                .setPositiveListener {
                    if (TextUtils.isEmpty(view.edit_input.text)) {
                        showToast(getString(R.string.express_input_order_toast))
                        return@setPositiveListener
                    }
                    it.dismiss()
                    ExpressManager.deleteExpress(number = mNumber)
                    mNumber = view.edit_input.text.toString()
                    tv_describe.text = "$mCompany:$mNumber"
                    ExpressManager.addExpress(mNumber, mCompany, mState, mName)
                    sendExpressChanged()
                    queryExpress(mNumber)
                }
                .createDialog()
        showKeyboard(view.edit_input)
    }

    // 修改备注
    private fun changeRemark() {
        val view = layoutInflater.inflate(R.layout.layout_dialog_input, null)
        val edit = view.edit_input
        edit.setText(mName)
        edit.selectAll()
        KonstantDialog(this)
                .setMessage(getString(R.string.express_input_remark))
                .addView(view)
                .setPositiveListener {
                    it.dismiss()
                    if (TextUtils.isEmpty(edit.text)) {
                        showToast(getString(R.string.express_input_remark_toast))
                        return@setPositiveListener
                    }
                    mName = edit.text.toString()
                    updateStatus()
                    ExpressManager.updateExpress(mNumber, null, mName, null)
                    sendExpressChanged()
                }
                .createDialog()
        showKeyboard(edit)
    }

    // 删除运单
    private fun deleteOrder() {
        KonstantDialog(this)
                .setMessage(getString(R.string.express_whether_delete))
                .setPositiveListener {
                    it.dismiss()
                    ExpressManager.deleteExpress(number = mNumber)
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
