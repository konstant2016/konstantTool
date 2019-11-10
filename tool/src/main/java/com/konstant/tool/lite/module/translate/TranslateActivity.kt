package com.konstant.tool.lite.module.translate

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.google.gson.Gson
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.view.KonstantArrayAdapter
import kotlinx.android.synthetic.main.activity_base.*
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
        setTitle(getString(R.string.translate_title))
        initBaseViews()
    }

    override fun initBaseViews() {
        super.initBaseViews()

        // 防止键盘遮挡布局
        addLayoutListener(base_content, btn_translate)

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
                showToast(getString(R.string.base_input_empty_toast))
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
        val disposable = NetworkHelper.getTranslate(string, typeFrom, typeTo)
                .subscribe({
                    tv_result.text = it.trans_result[0].dst
                }, {
                    tv_result.text = getString(R.string.translate_error);it.printStackTrace()
                })
        mDisposable.add(disposable)
    }
}
