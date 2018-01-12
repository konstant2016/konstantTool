package com.konstant.konstanttools.ui.activity.toolactivity

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.konstant.konstanttools.R
import com.konstant.konstanttools.server.Service
import com.konstant.konstanttools.server.response.PhoneLocationResponse
import com.konstant.konstanttools.base.BaseActivity
import com.konstant.konstanttools.util.KeyConstant
import com.konstant.konstanttools.util.UrlConstant
import kotlinx.android.synthetic.main.activity_phone_location.*

class PhoneLocationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_location)
        setTitle("号码归属地查询")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()
        btn_query.setOnClickListener {
            if (TextUtils.isEmpty(edit_phone?.text)) {
                Toast.makeText(this, "记得输入号码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (edit_phone.text.length < 7) {
                Toast.makeText(this, "手机号码长度不够", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val phone = edit_phone.text.toString()
            Service.queryPhoneLocation(UrlConstant.PHONE_LOCATION, phone, KeyConstant.PHONE_LOCATION) { state, data ->
                Log.i("号码归属地返回信息", data)
                if (!state) return@queryPhoneLocation
                val response = JSON.parseObject(data, PhoneLocationResponse::class.java)
                if (response.resultcode != 200) return@queryPhoneLocation
                updateViews(response)
            }
        }

    }

    private fun updateViews(response: PhoneLocationResponse) {
        runOnUiThread {
            tv_phone.text = "查询号码：${edit_phone.text}"
            tv_province.text = "省份：${response.result.province}"
            tv_city.text = "城市：${response.result.city}"
            tv_company.text = "运营商：${response.result.company}"
            tv_areacode.text = "区号：${response.result.areacode}"
        }
    }
}
