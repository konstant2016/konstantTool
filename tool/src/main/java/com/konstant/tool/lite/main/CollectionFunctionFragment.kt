package com.konstant.tool.lite.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.base.CollectionFunctionChanged
import com.konstant.tool.lite.data.bean.main.Function
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.fragment_function_collection.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 作者：konstant
 * 时间：2019/11/10 10:16
 * 描述：我的功能收藏列表
 */

class CollectionFunctionFragment : BaseFragment() {

    private val mFunctionList = ArrayList<Function>()
    private val mAdapter = AdapterMainConfig(mFunctionList)

    companion object {
        fun getInstance() = CollectionFunctionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_function_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFunctionList.clear()
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
            adapter = mAdapter
        }

        updateView()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCollectionFunctionChanged(msg: CollectionFunctionChanged) {
        mFunctionList.clear()
        mFunctionList.addAll(FunctionCollectorManager.getCollectionFunction())
        updateView()
        mAdapter.notifyDataSetChanged()
    }

    private fun updateView() {
        if (mFunctionList.size == 0) {
            collection_empty.visibility = View.VISIBLE
            recycler_main.visibility = View.GONE
        } else {
            collection_empty.visibility = View.GONE
            recycler_main.visibility = View.VISIBLE
        }
    }

}