package com.konstant.develop.jetpack.paging3.coroutines

import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.konstant.develop.jetpack.paging3.ItemPaging3
import com.konstant.develop.jetpack.paging3.NetStateHelper
import kotlinx.coroutines.delay
import java.net.ConnectException

/**
 * 时间：2022/5/29 00:35
 * 作者：吕卡
 * 备注：使用viewModel+协程的形式来请求数据
 */

class Paging3Coroutines {

    private val repository by lazy {
        val config = PagingConfig(pageSize = 10, initialLoadSize = 5, maxSize = 150)
        val sourceFactory = object : PagingSource<Int, ItemPaging3>() {
            /**
             * 官方解释：每当paging想要加载新的数据来代替当前列表时，会发生刷新操作，回调到这个方法
             *
             * 使用场景：
             * 比如说你当前是第三页，然后用户此时回到第二页时，数据发生变化了，不再前面加载好的数据了，
             * 此时就可以在这里返回第二页的索引，它会重新请求第二页的内容用来展示
             *
             * 目前还没有遇到过这种需求
             */
            override fun getRefreshKey(state: PagingState<Int, ItemPaging3>): Int? {
                return null
            }

            /**
             * 我这里定义的为每页显示10条数据
             * 模拟网络加载失败的情况
             */
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemPaging3> {
                val currentPage = params.key ?: 0
                Log.d("ItemDataSource", "currentPage:$currentPage")
                if (currentPage > 0) {
                    val random = Math.random()
                    delay((random * 3000).toLong())
                }
                val list = mutableListOf<ItemPaging3>()
                for (i in 0..9) {
                    list.add(ItemPaging3("第" + (10 * currentPage + i) + "条"))
                }
                val prevKey = if (currentPage == 0) null else currentPage - 1
                val nextKey = currentPage + 1
                if (!NetStateHelper.isConnect) {
                    return LoadResult.Error(ConnectException())
                }
                if (currentPage == 4) {
                    // nextKey为null表示数据加载到头了
                    return LoadResult.Page(list, prevKey, null)
                }
                return LoadResult.Page(list, prevKey, nextKey)
            }
        }
        Pager(config, pagingSourceFactory = { sourceFactory })
    }

    fun getData() = repository.flow.asLiveData()

}