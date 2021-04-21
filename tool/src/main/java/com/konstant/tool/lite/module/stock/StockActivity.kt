package com.konstant.tool.lite.module.stock

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.view.KonstantDialog
import kotlinx.android.synthetic.main.activity_stock.*
import kotlinx.android.synthetic.main.layout_dialog_add_stock.view.*
import kotlinx.android.synthetic.main.title_layout.*

class StockActivity : BaseActivity() {

    private val mStockList = mutableListOf<StockData>()
    private val mAdapter by lazy { AdapterStock(mStockList) }
    private val mPresenter by lazy { StockPresenter(mDisposable) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        setTitle("股票推算")
        initViews()
        getStockList()
    }

    private fun initViews() {
        recycler_view.adapter = mAdapter
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener { showAddDialog() }
        mAdapter.setOnItemLongClickListener { _, position ->
            if (position in 0 until mStockList.size) {
                showDeleteDialog(position)
            }
        }
    }

    private fun getStockList() {
        val stockList = mPresenter.getStockList()
        if (stockList.isNotEmpty()) {
            tv_tips.visibility = View.GONE
            stockList.forEach {
                getStockDetail(it)
            }
        } else {
            tv_tips.visibility = View.VISIBLE
        }
    }

    private fun getStockDetail(stockData: StockData) {
        mPresenter.getStockDetail(stockData, {
            if (mStockList.contains(it)) return@getStockDetail
            mStockList.add(it)
            mAdapter.notifyDataSetChanged()
        }, {

        })
    }

    private fun showAddDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_add_stock, null)
        KonstantDialog(this)
                .addView(view)
                .setPositiveListener { dialog ->
                    val name = view.et_name.text
                    if (TextUtils.isEmpty(name)) {
                        showToast("记得输入股票名称")
                        return@setPositiveListener
                    }
                    val number = view.et_number.text
                    if (TextUtils.isEmpty(number)) {
                        showToast("记得输入股票代码")
                        return@setPositiveListener
                    }
                    val count = view.et_count.text
                    if (TextUtils.isEmpty(count)) {
                        showToast("记得输入持仓数量哦")
                        return@setPositiveListener
                    }
                    dialog.dismiss()
                    val stockData = StockData(name.toString(), number.toString(), count.toString().toDouble())
                    mPresenter.addStock(stockData, {
                        if (mStockList.contains(it)) return@addStock
                        mStockList.add(it)
                        mAdapter.notifyDataSetChanged()
                    }, {

                    })
                }
                .createDialog()
        showKeyboard(view.et_name)
    }

    private fun showDeleteDialog(position: Int) {
        KonstantDialog(this)
                .setTitle("提示")
                .setMessage("确认删除这支股票吗？")
                .setPositiveListener {
                    mPresenter.deleteStock(mStockList[position])
                    mStockList.removeAt(position)
                    mAdapter.notifyDataSetChanged()
                    it.dismiss()
                }
                .createDialog()
    }

}