package com.konstant.toollite.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.konstant.toollite.R
import com.konstant.toollite.base.BaseActivity
import com.konstant.toollite.server.Service
import com.konstant.toollite.server.response.TranslateResponse
import com.konstant.toollite.util.KeyConstant
import com.konstant.toollite.util.UrlConstant
import com.konstant.toollite.view.KonstantArrayAdapter
import kotlinx.android.synthetic.main.activity_translate.*

/**
 * 描述:翻译
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午9:09
 * 备注:
 */

class TranslateActivity : BaseActivity() {

    private val languageNames by lazy { resources.getStringArray(R.array.translate_type_name) }
    private val languageShorts by lazy { resources.getStringArray(R.array.translate_type_short) }

    private var typeFrom = "auto"
    private var typeTo = "zh"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)
        setTitle("翻译")
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 隐藏键盘
        layout_bg.setOnClickListener { hideSoftKeyboard() }

        // 防止键盘遮挡布局
        addLayoutListener(layout_bg, btn_translate)

        // 初始化左边的spinner
        val adapterOrigin = KonstantArrayAdapter(this,R.layout.item_spinner_bg,languageNames)
        spinner_origin.adapter = adapterOrigin
        spinner_origin.setSelection(0)
        spinner_origin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                typeFrom = languageShorts[position]
                hideSoftKeyboard()
            }
        }


        // 初始化右边的spinner
        val typeName = languageNames.copyOfRange(1,languageNames.size)
        val adapterResult = KonstantArrayAdapter(this, R.layout.item_spinner_bg, typeName)
        spinner_result.adapter = adapterResult
        spinner_result.setSelection(0)
        spinner_result.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                typeTo = languageShorts[position + 1]
                hideSoftKeyboard()
            }
        }

        // 翻译按钮按下后
        btn_translate.setOnClickListener {
            hideSoftKeyboard()
            if (TextUtils.isEmpty(et_query.text)) {
                Toast.makeText(this, "你想翻译啥？", Toast.LENGTH_SHORT).show()
            } else {
                doTranslate(et_query.text.toString())
            }
        }

        // 清空键按下后
        btn_clean.setOnClickListener {
            hideSoftKeyboard()
            et_query.setText("")
            tv_result.setText("")
        }
    }

    // 调用接口进行翻译
    private fun doTranslate(string: String) {
        Service.translate(this,UrlConstant.TRANSLATE_URL, string, typeFrom, typeTo,
                KeyConstant.TRANSLATE_APP_ID, KeyConstant.TRANSLATE_SECRET) { _, data ->
            showTranslateResult(data)
        }
    }

    // 展示翻译结果
    private fun showTranslateResult(string: String) {
        runOnUiThread {
            val result = JSON.parseObject(string, TranslateResponse::class.java)
            tv_result.text = result.trans_result[0].dst
        }
    }
}
