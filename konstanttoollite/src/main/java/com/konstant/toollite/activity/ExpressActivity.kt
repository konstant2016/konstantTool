package com.konstant.toollite.activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.adapter.AdapterExpress
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.server.other.ExpressData
import com.konstant.toollite.util.Constant
import com.konstant.toollite.util.FileUtils
import com.konstant.toollite.view.KonstantArrayAdapter
import kotlinx.android.synthetic.main.activity_express.*
import kotlinx.android.synthetic.main.title_layout.*

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

    override fun initBaseViews() {
        super.initBaseViews()
        img_more.visibility = View.VISIBLE

        listview_express.adapter = mAdapter

        img_more.setOnClickListener { addExpress() }

        listview_express.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, ExpressDetailActivity::class.java)
            intent.putExtra("mCompanyId", expressList[position].company)
            intent.putExtra("mOrderNo", expressList[position].orderNo)
            intent.putExtra("remark", expressList[position].remark)
            startActivity(intent)
        }

        listview_express.setOnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否删除此记录？")
                    .setPositiveButton("确定") { dialog, _ ->
                        dialog.dismiss()
                        val data = expressList[position]
                        expressList.remove(data)
                        mAdapter.notifyDataSetChanged()
                        updateUi()
                        removeexpress(data)
                    }
                    .setNegativeButton("取消") { dialog, _ ->
                    }
                    .create().show()
            true
        }
    }

    // 更新界面
    fun updateUi(){
        if(expressList.isEmpty()){
            tv_tips.visibility = View.VISIBLE
            listview_express.visibility = View.GONE
        }else{
            tv_tips.visibility = View.GONE
            listview_express.visibility = View.VISIBLE
        }
    }

    // 读取本地保存的物流信息
    fun readLocalExpress() {
        val express = FileUtils.readDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS)
        if (!express.isNullOrEmpty()) {
            val list = JSON.parseArray(express, ExpressData::class.java)
            expressList.addAll(list)
            mAdapter.notifyDataSetChanged()
        }
        updateUi()
    }

    fun removeexpress(expressData: ExpressData) {
        val s = FileUtils.readDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS)
        val list = JSON.parseArray(s, ExpressData::class.java)
        list.remove(expressData)
        FileUtils.saveDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS, JSON.toJSONString(list))
    }

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
        AlertDialog.Builder(this)
                .setTitle("填写物流信息")
                .setView(view)
                .setPositiveButton("确定") { dialog, _ ->
                    if (TextUtils.isEmpty(et_num.text)) {
                        Toast.makeText(this, "记得输入单号哦", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    expressQuery(companyId, et_num.text.toString())
                    var remark = "保密物件"
                    if (!TextUtils.isEmpty(et_remark.text)) {
                        remark = et_remark.text.toString()
                    }
                    val data = ExpressData(companyId, et_num.text.toString(), remark, "暂无物流")
                    expressList.add(data)
                    dialog.dismiss()
                }
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()

    }

    fun expressQuery(companyId: String, num: String) {
        val intent = Intent(this, ExpressDetailActivity::class.java)
        intent.putExtra("mCompanyId", companyId)
        intent.putExtra("mOrderNo", num)
        startActivity(intent)
    }

    fun makeToast(string: String) {
        runOnUiThread {
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        val s = JSON.toJSONString(expressList)
        FileUtils.saveDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS, s)
    }


    override fun onResume() {
        super.onResume()
        val s = FileUtils.readDataWithSharedPreference(this, Constant.NAME_LOCAL_EXPRESS)
        Log.d("读取到的本地物流信息", "读取到的本地物流信息$s")
        if (s.isNullOrEmpty()) return
        val list = JSON.parseArray(s, ExpressData::class.java)
        expressList.clear()
        expressList.addAll(list)
        mAdapter.notifyDataSetChanged()
        updateUi()
    }
}
