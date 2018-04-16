package com.konstant.tool.lite.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.konstant.tool.lite.R
import com.konstant.tool.lite.adapter.AdapterExpress
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.ExpressData
import com.konstant.tool.lite.data.ExpressManager
import com.konstant.tool.lite.view.KonstantConfirmtDialog
import com.konstant.tool.lite.view.KonstantArrayAdapter
import com.konstant.tool.lite.view.KonstantViewDialog
import kotlinx.android.synthetic.main.activity_express.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * 描述:物流列表页
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:11
 * 备注:
 */

@SuppressLint("MissingSuperCall")
class ExpressActivity : BaseActivity() {

    private val expressList = ArrayList<ExpressData>()
    private val mAdapter by lazy { AdapterExpress(this, expressList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_express)
        setTitle("物流查询")
        initBaseViews()

        readLocalExpress()
    }

    // 初始化基础控件
    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE

        listview_express.adapter = mAdapter

        img_more.setOnClickListener { addExpress() }

        listview_express.setOnItemClickListener { _, _, position, _ ->
            val data = expressList[position]
            expressQuery(data.company, data.orderNo, data.remark)
        }

        listview_express.setOnItemLongClickListener { parent, view, position, id ->
            KonstantConfirmtDialog(this)
                    .setMessage("是否删除此记录？")
                    .setPositiveListener {
                        it.dismiss()
                        val data = expressList[position]
                        expressList.remove(data)
                        mAdapter.notifyDataSetChanged()
                        updateUI()

                        ExpressManager.deleteExpress(this, data.orderNo)
                    }
                    .setNegativeListener {  }
                    .show()
            true
        }
    }

    // 更新界面
    private fun updateUI() {
        if (expressList.isEmpty()) {
            tv_tips.visibility = View.VISIBLE
            listview_express.visibility = View.GONE
        } else {
            tv_tips.visibility = View.GONE
            listview_express.visibility = View.VISIBLE
        }
    }

    // 读取本地保存的物流信息
    private fun readLocalExpress() {
        val list = ExpressManager.readExpress(this)
        Log.d(this.localClassName,list.toString())
        expressList.clear()
        expressList.addAll(list)
        mAdapter.notifyDataSetChanged()
    }


    // 添加物流查询
    fun addExpress() {
        val view = layoutInflater.inflate(R.layout.layout_pop_express, null)
        val et_num = view.findViewById(R.id.et_num) as EditText
        val et_remark = view.findViewById(R.id.et_remark) as EditText
        val spinner = view.findViewById(R.id.spinner_company) as Spinner
        val commanyArr = this.resources.getStringArray(R.array.express_company)
        val companyIds = this.resources.getStringArray(R.array.express_company_id)
        var companyId = "shunfeng"
        spinner.adapter = KonstantArrayAdapter(this, R.layout.item_spinner_bg, commanyArr)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                companyId = companyIds[position]
            }
        }

        KonstantViewDialog(this)
                .setMessage("添加物流信息")
                .setPositiveListener {
                    var remark = "保密物件"
                    if (!TextUtils.isEmpty(et_remark.text)) {
                        remark = et_remark.text.toString()
                    }
                    if (TextUtils.isEmpty(et_num.text)) {
                        Toast.makeText(this, "记得输入运单号哦", Toast.LENGTH_SHORT).show()
                        return@setPositiveListener
                    }
                    it.dismiss()
                    expressQuery(companyId, et_num.text.toString(), remark)
                }
                .addView(view)
                .show()
    }

    // 跳转到快递查询页面
    fun expressQuery(companyId: String, num: String, remark: String) {
        val intent = Intent(this, ExpressDetailActivity::class.java)
        intent.putExtra("mCompanyId", companyId)
        intent.putExtra("mOrderNo", num)
        intent.putExtra("mRemark", remark)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        readLocalExpress()
        updateUI()



    }
}
