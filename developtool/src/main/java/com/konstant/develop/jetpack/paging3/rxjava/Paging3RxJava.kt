package com.konstant.develop.jetpack.paging3.rxjava

import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.konstant.develop.jetpack.paging3.ItemPaging3
import com.konstant.develop.jetpack.paging3.NetStateHelper
import io.reactivex.Single
import java.net.ConnectException
import java.util.concurrent.TimeUnit

/**
 * 时间：2022/5/29 00:39
 * 作者：吕卡
 * 备注：Paging3与RxJava结合使用
 */

class Paging3RxJava {

    private val repository by lazy {

        val config = PagingConfig(pageSize = 10, initialLoadSize = 5, maxSize = 150)

        val sourceFactory = object : RxPagingSource<Int, ItemPaging3>() {
            override fun getRefreshKey(state: PagingState<Int, ItemPaging3>): Int? {
                return null
            }

            override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, ItemPaging3>> {
                val currentPage = params.key ?: 0
                val delay = if (currentPage > 0) {
                    val random = Math.random()
                    random * 3000
                } else {
                    0f
                }.toLong()
                return Single.just("")
                    .map {
                        val list = mutableListOf<ItemPaging3>()
                        for (i in 0..(Math.random() * 10).toInt()) {
                            list.add(ItemPaging3("第" + (10 * currentPage + i) + "条"))
                        }
                        val prevKey = if (currentPage == 0) null else currentPage - 1
                        val nextKey = currentPage + 1
                        if (!NetStateHelper.isConnect) {
                            return@map LoadResult.Error(ConnectException())
                        }
                        if (currentPage == 4) {
                            // nextKey为null表示数据加载到头了
                            return@map LoadResult.Page(list, prevKey, null)
                        }
                        return@map LoadResult.Page(list, prevKey, nextKey)
                    }.delay(delay, TimeUnit.MILLISECONDS)
            }
        }
        Pager(config, pagingSourceFactory = { sourceFactory })
    }

    fun getData() = repository.flow.asLiveData()

}