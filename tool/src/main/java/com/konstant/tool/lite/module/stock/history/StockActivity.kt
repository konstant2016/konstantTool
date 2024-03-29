package com.konstant.tool.lite.module.stock.history

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.data.bean.stock.StockData
import com.konstant.tool.lite.module.stock.history.vertical.StockVerticalHistoryActivity
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
        setTitle(getString(R.string.stock_title))
        StockManager.onCreate(this)
        initViews()
        getStockList()
    }

    private fun initViews() {
        recycler_view.adapter = mAdapter
        img_more.visibility = View.VISIBLE
        img_more.setOnClickListener {
            startActivity(StockVerticalHistoryActivity::class.java)
        }
        mAdapter.setOnItemLongClickListener { _, position ->
            if (position in 0 until mStockList.size) {
                showDeleteDialog(position)
            }
        }
        mAdapter.setOnItemClickListener { _, position ->
            // 点击的添加按钮
            if (position == mAdapter.itemCount - 2) {
                showAddDialog()
            }
        }
    }

    private fun getStockList() {
        val stockList = mPresenter.getStockList()
        if (stockList.isNotEmpty()) {
            getStockDetail(stockList)
        }
    }

    private fun getStockDetail(stockList: List<StockData>) {
        mPresenter.getStockDetail(stockList, {
            onResult(it)
        }, {

        })
    }

    private fun showAddDialog() {
        val view = LayoutInflater.from(this).inflate(R.layout.layout_dialog_add_stock, null)
        var type = "sh"
        view.radio_type.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_sh -> {
                    type = "sh"
                }
                R.id.radio_sz -> {
                    type = "sz"
                }
            }
        }
        KonstantDialog(this)
            .addView(view)
            .setPositiveListener { dialog ->
                val number = view.et_number.text
                if (TextUtils.isEmpty(number)) {
                    showToast(getString(R.string.stock_remember_input_number))
                    return@setPositiveListener
                }
                val count = view.et_count.text
                if (TextUtils.isEmpty(count)) {
                    showToast(getString(R.string.stock_remember_input_count))
                    return@setPositiveListener
                }
                dialog.dismiss()

                val stockData = StockData("$type${number}", count.toString().toDouble())
                mPresenter.addStock(stockData, {
                    onResult(it)
                }, {

                })
            }
            .createDialog()
        showKeyboard(view.et_number)
    }

    private fun onResult(stockList: List<StockData>) {
        stockList.forEach { data ->
            if (!mStockList.any { it.number == data.number }) {
                mStockList.add(data)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun showDeleteDialog(position: Int) {
        KonstantDialog(this)
            .setTitle(getString(R.string.base_tips))
            .setMessage(getString(R.string.stock_delete_stock_tips))
            .setPositiveListener {
                mPresenter.deleteStock(mStockList[position])
                mStockList.removeAt(position)
                mAdapter.notifyDataSetChanged()
                it.dismiss()
            }
            .createDialog()
    }

    override fun onPause() {
        super.onPause()
        StockManager.saveToLocal(this)
    }

    override fun onDestroy() {
        StockManager.saveToLocal(this)
        super.onDestroy()
    }

}