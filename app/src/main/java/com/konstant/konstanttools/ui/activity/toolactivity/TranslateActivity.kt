package com.konstant.konstanttools.ui.activity.toolactivity

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
import com.konstant.konstanttools.R
import com.konstant.konstanttools.base.BaseActivity
import com.konstant.konstanttools.server.Service
import com.konstant.konstanttools.server.response.TranslateResult
import com.konstant.konstanttools.util.KeyConstant
import com.konstant.konstanttools.util.UrlConstant
import kotlinx.android.synthetic.main.activity_translate.*

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
        val adapterOrigin = object : ArrayAdapter<String>(this, R.layout.item_spinner_bg, languageNames) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val root = LayoutInflater.from(context).inflate(R.layout.item_spinner_pull_down_bg, parent, false)
                val tv = root.findViewById(R.id.text_label) as TextView
                tv.text = languageNames[position]
                return root
            }
        }
        adapterOrigin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
        val typeName = mutableListOf<String>()
        (1 until languageNames.size).forEach { typeName.add(languageNames[it]) }
        val adapterResult = object : ArrayAdapter<String>(this, R.layout.item_spinner_bg, typeName) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val root = LayoutInflater.from(context).inflate(R.layout.item_spinner_pull_down_bg, parent, false)
                val tv = root.findViewById(R.id.text_label) as TextView
                tv.text = typeName[position]
                return root
            }
        }
        adapterResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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
        Service.translate(UrlConstant.TRANSLATE_URL, string, typeFrom, typeTo,
                KeyConstant.TRANSLATE_APP_ID, KeyConstant.TRANSLATE_SECRET) { state, data ->
            showTranslateResult(data)
        }
    }

    // 展示翻译结果
    private fun showTranslateResult(string: String) {
        runOnUiThread {
            val result = JSON.parseObject(string, TranslateResult::class.java)
            tv_result.setText(result.trans_result[0].dst)
        }
    }
}
