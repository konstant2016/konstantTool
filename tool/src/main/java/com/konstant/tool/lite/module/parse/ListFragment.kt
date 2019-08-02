package com.konstant.tool.lite.module.parse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_net_list.*

/**
* 时间：2019/8/2 15:30
* 创建：菜籽
* 描述：各大视频网站入口
*/

class ListFragment : BaseFragment() {

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_net_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_tencent.setOnClickListener {onBtnClick("https://v.qq.com") }
        btn_youku.setOnClickListener { onBtnClick("https://www.youku.com")}
        btn_sohu.setOnClickListener {onBtnClick("https://tv.sohu.com") }
        btn_iqiyi.setOnClickListener {onBtnClick("https://www.iqiyi.com") }
        btn_pptv.setOnClickListener { onBtnClick("http://www.pptv.com")}
        btn_letv.setOnClickListener { onBtnClick("http://www.le.com")}
    }

    private fun onBtnClick(url: String) {
        with(Intent(mActivity,NetVideoActivity::class.java)){
            putExtra("url",url)
            startActivity(this)
        }
    }
}
