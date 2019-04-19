package com.konstant.tool.lite.module.busline.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseFragment
import com.konstant.tool.lite.module.busline.adapter.AdapterQueryHistory
import com.konstant.tool.lite.module.busline.data.QueryHistoryManager
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.fragment_history.*
import org.greenrobot.eventbus.EventBus


class HistoryFragment : BaseFragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val mQueryList = QueryHistoryManager.getQueryHistory()
    private val mAdapterHistory by lazy { AdapterQueryHistory(mQueryList) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapterHistory.apply {
            setOnItemClickListener { _, position ->
                EventBus.getDefault().post(mQueryList[position])
            }

            setOnItemLongClickListener { _, position ->
                KonstantDialog(mActivity)
                        .setMessage("是否删除此条记录？")
                        .setPositiveListener {
                            QueryHistoryManager.removeQueryHistory(mQueryList[position])
                            updateData(position)
                            it.dismiss()
                        }
                        .createDialog()
            }
        }

        recycler_history.apply {
            layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapterHistory
        }
    }

    override fun onFragmentResume() {
        updateData()
    }

    private fun updateData(position: Int = -1) {
        if (position == -1) {
            mAdapterHistory.notifyDataSetChanged()
        } else {
            mAdapterHistory.notifyItemRemoved(position)
        }
        tv_none.visibility = if (mQueryList.isEmpty()) View.VISIBLE else View.GONE
        recycler_history.visibility = if (mQueryList.isEmpty()) View.GONE else View.VISIBLE
    }


}
