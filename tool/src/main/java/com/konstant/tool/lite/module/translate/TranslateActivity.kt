package com.konstant.tool.lite.module.translate

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.module.translate.server.TranslateResponse
import com.konstant.tool.lite.network.Constant
import com.konstant.tool.lite.network.NetService
import com.konstant.tool.lite.view.KonstantArrayAdapter
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
        val adapterOrigin = KonstantArrayAdapter(this, languageNames.toList())
        spinner_origin.apply {
            adapter = adapterOrigin
            setSelection(0)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    typeFrom = languageShorts[position]
                    hideSoftKeyboard()
                }
            }
        }

        // 初始化右边的spinner
        val typeName = languageNames.copyOfRange(1, languageNames.size)
        val adapterResult = KonstantArrayAdapter(this, typeName.toList())
        spinner_result.apply {
            adapter = adapterResult
            setSelection(0)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    typeTo = languageShorts[position + 1]
                    hideSoftKeyboard()
                }
            }
        }

        // 翻译按钮按下后
        btn_translate.setOnClickListener {
            hideSoftKeyboard()
            if (TextUtils.isEmpty(et_query.text)) {
                showToast("你想翻译啥？")
            } else {
                doTranslate(et_query.text.toString())
            }
        }

        // 清空键按下后
        btn_clean.setOnClickListener {
            hideSoftKeyboard()
            et_query.setText("")
            tv_result.text = ""
        }
    }

    // 调用接口进行翻译
    private fun doTranslate(string: String) {
        NetService.translate(Constant.URL_TRANSLATE, string, typeFrom, typeTo,
                Constant.KEY_TRANSLATE_APP_ID, Constant.KEY_TRANSLATE_SECRET) {
            showTranslateResult(it)
        }
    }

    // 展示翻译结果
    private fun showTranslateResult(result: TranslateResponse) {
        runOnUiThread {
            if (result.trans_result == null || result.trans_result.size == 0) {
                tv_result.text = "翻译出错"
                return@runOnUiThread
            }
            tv_result.text = result.trans_result[0].dst
        }
    }
}
