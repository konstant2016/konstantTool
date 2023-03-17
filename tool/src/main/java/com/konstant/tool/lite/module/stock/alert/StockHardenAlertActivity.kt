package com.konstant.tool.lite.module.stock.alert

import android.os.Bundle
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.network.NetworkHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StockHardenAlertActivity : BaseActivity() {

    private val alertDispose = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_harden_alert)
        setTitle(getString(R.string.stock_harden_alert_title))
        startCountDown()
    }

    /**
     * 开始倒计时
     */
    private fun startCountDown() {
        val dispose = Observable.interval(0, 1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getHardenAlert(it)
                updateDescribe(it)
            }, {
                it.printStackTrace()
            })
        mDisposable.add(dispose)
    }

    private fun getHardenAlert(times: Long) {
        alertDispose.clear()
        val dispose = NetworkHelper.getStockBrief("")
            .subscribe({

            }, {
                it.printStackTrace()
            })
        alertDispose.add(dispose)
    }

    private fun updateDescribe(times: Long){

    }

    override fun onDestroy() {
        super.onDestroy()
        alertDispose.dispose()
    }

}