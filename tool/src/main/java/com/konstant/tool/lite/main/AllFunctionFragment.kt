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
import com.konstant.tool.lite.data.bean.main.Function
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * 作者：konstant
 * 时间：2019/11/10 10:16
 * 描述：全部功能列表
 */

class AllFunctionFragment : BaseFragment() {

    companion object {
        fun getInstance() = AllFunctionFragment()
    }

    private val mFunctionList by lazy {
        val config = KonApplication.context.assets.open("MainFunction.json").bufferedReader().readText()
        Gson().fromJson<List<Function>>(config, object : TypeToken<List<Function>>() {}.type)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AdapterMainConfig(mFunctionList)
        adapter.setOnItemClickListener { _, position ->
            activity?.let {
                (activity as BaseActivity).startActivityWithType(mFunctionList[position].type)
            }
        }

        adapter.setOnItemLongClickListener { _, position ->
            activity?.let {
                KonstantDialog(activity!!)
                        .setMessage("${getString(R.string.base_collection)}'${mFunctionList[position].title}'${getString(R.string.base_function)}?")
                        .setPositiveListener {
                            FunctionCollectorManager.addCollectionFunction(mFunctionList[position])
                            showToast(getString(R.string.base_collection_success))
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