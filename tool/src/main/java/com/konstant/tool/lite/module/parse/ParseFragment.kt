package com.konstant.tool.lite.module.parse

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.H5Activity
import com.konstant.tool.lite.base.KonApplication
import kotlinx.android.synthetic.main.fragment_parse.*

/**
 * 时间：2019/8/1 18:03
 * 创建：菜籽
 * 描述：直接输入地址进行视频解析
 */

class ParseFragment : BaseFragment() {

    private val mUrls by lazy { KonApplication.context.resources.getStringArray(R.array.list_parse_url) }
    private var baseUrl = mUrls[0]

    companion object {
        @JvmStatic
        fun newInstance() = ParseFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parse, container, false)
    }

    override fun onFragmentResume() {
        setTitle("网址解析页面")
        setSubTitle("右滑进入视频网站列表")
    }

    override fun onLazyLoad() {
        setTitle("VIP视频解析")
        layout_content.setOnClickListener { hideSoftKeyboard() }
        radio_group.setOnCheckedChangeListener { _, checkedId ->
            baseUrl = when (checkedId) {
                R.id.radio_01 -> mUrls[0]
                R.id.radio_02 -> mUrls[1]
                R.id.radio_03 -> mUrls[2]
                R.id.radio_04 -> mUrls[3]
                R.id.radio_05 -> mUrls[4]
                else -> mUrls[0]
            }
        }

        btn_ok.setOnClickListener {
            if (TextUtils.isEmpty(edit_input.text)) {
                showToast("记得输入地址哦");return@setOnClickListener
            }
            val url = if (edit_input.text.startsWith("http://") || edit_input.text.startsWith("https://")) {
                edit_input.text.toString()
            } else {
                "http://" + edit_input.text
            }
            with(Intent(mActivity, H5Activity::class.java)) {
                putExtra(H5Activity.H5_URL, baseUrl + url)
                putExtra(H5Activity.H5_BROWSER, true)
                startActivity(this)
            }
        }
    }

}
