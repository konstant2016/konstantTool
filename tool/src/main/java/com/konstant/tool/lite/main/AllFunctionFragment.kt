package com.konstant.tool.lite.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.data.bean.main.ConfigData
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.layout_recycler_view.*

class AllFunctionFragment() : BaseFragment() {

    companion object {
        fun getInstance() = AllFunctionFragment()
    }

    private val mConfigs by lazy {
        val config = KonApplication.context.assets.open("MainConfig.json").bufferedReader().readText()
        Gson().fromJson<List<ConfigData>>(config, object : TypeToken<List<ConfigData>>() {}.type)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterMainConfig(mConfigs)
        adapter.setOnItemClickListener { _, position ->
            activity?.let {
                (activity as BaseActivity).startActivityWithType(mConfigs[position].type)
            }
        }

        adapter.setOnItemLongClickListener { _, position ->
            activity?.let {
                KonstantDialog(activity!!)
                        .setMessage("收藏'${mConfigs[position].title}'功能?")
                        .setPositiveListener {
                            FunctionCollectorManager.addCollectionFunction(mConfigs[position])
                            showToast("收藏成功")
                            it.dismiss()
                        }
                        .createDialog()
            }
        }

        recycler_main.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setAdapter(adapter)
        }
    }

}