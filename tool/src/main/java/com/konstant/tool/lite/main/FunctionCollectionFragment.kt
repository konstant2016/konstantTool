package com.konstant.tool.lite.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.data.bean.main.Function
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.layout_recycler_view.*

/**
 * 作者：konstant
 * 时间：2019/11/10 10:16
 * 描述：我的功能收藏列表
 */

class FunctionCollectionFragment : BaseFragment() {

    private val mFunctionList = ArrayList<Function>()
    private val mAdapter = AdapterMainConfig(mFunctionList)

    companion object {
        fun getInstance() = FunctionCollectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFunctionList.addAll(FunctionCollectorManager.getCollectionFunction())
        mAdapter.setOnItemClickListener { _, position ->
            activity?.let {
                (activity as BaseActivity).startActivityWithType(mFunctionList[position].type)
            }
        }

        mAdapter.setOnItemLongClickListener { _, position ->
            activity?.let {
                KonstantDialog(activity!!)
                        .setMessage("${getString(R.string.main_cancel_collection)}'${mFunctionList[position].title}'${getString(R.string.base_function)}?")
                        .setPositiveListener {
                            FunctionCollectorManager.removeCollectionFunction(mFunctionList[position])
                            showToast(getString(R.string.main_cancel_collection_success))
                            mFunctionList.clear()
                            mFunctionList.addAll(FunctionCollectorManager.getCollectionFunction())
                            mAdapter.notifyDataSetChanged()
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
            setAdapter(mAdapter)
        }
    }

    override fun onResume() {
        super.onResume()
        mFunctionList.clear()
        mFunctionList.addAll(FunctionCollectorManager.getCollectionFunction())
        mAdapter.notifyDataSetChanged()
    }

}