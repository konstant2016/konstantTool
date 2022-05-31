package com.konstant.develop.jetpack.paging3

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.konstant.develop.R
import com.konstant.develop.base.BaseActivity
import com.konstant.develop.jetpack.paging3.coroutines.Paging3Coroutines
import com.konstant.develop.jetpack.paging3.rxjava.Paging3RxJava
import kotlinx.android.synthetic.main.activity_paging_3.*

/**
 * 时间：2022/5/28 21:06
 * 作者：吕卡
 * 备注：使用paging3实现分页加载
 */

class Paging3Activity : BaseActivity() {

    private val adapter by lazy { AdapterPaging3() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging_3)
        initBaseViews()
        //withCoroutines()
        withRxJava()
    }

    private fun initBaseViews() {
        net_switch.setOnClickListener {
            NetStateHelper.isConnect = net_switch.isChecked
        }

        val loadAdapter = LoadStateAdapterPaging3()
        loadAdapter.setOnReloadClickListener {
            adapter.retry()
        }
        val connectAdapter = adapter.withLoadStateFooter(loadAdapter)
        recycler_view.adapter = connectAdapter
    }

    private fun withCoroutines() {
        val coroutines = Paging3Coroutines()
        coroutines.getData().observe(this) {
            lifecycleScope.launchWhenCreated {
                adapter.submitData(it)
            }
        }
    }

    private fun withRxJava() {
        val rxJava = Paging3RxJava()
        rxJava.getData().observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

}