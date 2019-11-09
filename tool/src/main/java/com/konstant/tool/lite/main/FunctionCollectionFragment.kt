package com.konstant.tool.lite.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.data.bean.main.ConfigData
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.layout_recycler_view.*

class FunctionCollectionFragment : BaseFragment() {

    private val mConfigs = ArrayList<ConfigData>()

    companion object {
        fun getInstance() = FunctionCollectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mConfigs.addAll(FunctionCollectorManager.getCollectionFunction())
        val adapter = AdapterMainConfig(mConfigs)
        adapter.setOnItemClickListener { _, position ->
            activity?.let {
                (activity as BaseActivity).startActivityWithType(mConfigs[position].type)
            }
        }

        adapter.setOnItemLongClickListener { _, position ->
            activity?.let {
                KonstantDialog(activity!!)
                        .setMessage("取消收藏'${mConfigs[position].title}'功能?")
                        .setPositiveListener {
                            FunctionCollectorManager.removeCollectionFunction(mConfigs[position])
                            showToast("已取消收藏")
                            mConfigs.clear()
                            mConfigs.addAll(FunctionCollectorManager.getCollectionFunction())
                            adapter.notifyDataSetChanged()
                            it.dismiss()
                            if (FunctionCollectorManager.getCollectionFunction().isEmpty()) {
                                activity?.recreate()
                            }
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