package com.konstant.tool.lite.module.live

import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.network.response.TvLiveResponse
import io.reactivex.disposables.CompositeDisposable

class TvLivePresenter(private val mDisposable: CompositeDisposable) {

    fun getLiveList(success: (List<TvLiveResponse.ResultsBean>) -> Unit, error: () -> Unit) {
        val subscribe = NetworkHelper.getTvLiveList()
                .subscribe({
                    it.results.sortBy { it.sort }
                    success.invoke(it.results)
                }, {
                    it.printStackTrace()
                    error.invoke()
                })
        mDisposable.add(subscribe)
    }

}