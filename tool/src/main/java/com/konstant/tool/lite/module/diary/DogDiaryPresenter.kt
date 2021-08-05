package com.konstant.tool.lite.module.diary

import com.konstant.tool.lite.network.NetworkHelper
import io.reactivex.disposables.CompositeDisposable

class DogDiaryPresenter(private val mDispose: CompositeDisposable) {

    fun getDogDiaryContent(content: (String) -> Unit) {
        val dispose = NetworkHelper.getDogDiary()
                .subscribe({
                    content.invoke(it)
                }, {
                    content.invoke("")
                })
        mDispose.add(dispose)
    }

}