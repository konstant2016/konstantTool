package com.yangcong345.kratos.resourse

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yangcong345.context_provider.ContextProvider
import com.yangcong345.platform.net.ServiceFactory
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.io.InputStream

object CacheManager {

    private const val SP_NAME = "PAGE_SOURCE_NAME"
    private const val SP_KEY = "PAGE_SOURCE_CACHE"
    private val service = ServiceFactory.newService(SourceServiceApi::class.java)

    private val sourceList = mutableListOf<PageSource>()

    /**
     * 预加载资源
     */
    fun preloadSource(pageList: List<String>) {

    }

    /**
     * 根据页面路由获取资源数据
     */
    fun getSourceBundle(pagePath: String, result: PageResourceCallback) {
        result.onStart(pagePath)
        if (sourceList.isEmpty()) readSourceList()
        val source = sourceList.find { it.pageName == pagePath }
        if (source != null) {
            val bundle = LocalHelper.getCacheSourceBundle(source)
            if (bundle != null) {
                result.onSuccess(bundle)
                return
            }
        }
        service.getPageSource(pagePath)
            .flatMap { pageSource ->
                val dslObservable = service.getFileStream(pageSource.dsl.url)
                val jsObservable = service.getFileStream(pageSource.code.url)
                Observable.zip(dslObservable, jsObservable, BiFunction { dsl, js ->
                    return@BiFunction Triple(pageSource, dsl.byteStream(), js.byteStream())
                })
            }.subscribeOn(Schedulers.io())
            .onErrorReturnItem(buildSource())
            .subscribe({
                LocalHelper.saveCacheSource(it.first, it.second, it.third)
                val bundle = LocalHelper.getCacheSourceBundle(it.first)!!
                sourceList.add(it.first)
                saveSourceList()
                result.onSuccess(bundle)
            }, {
                result.onFail(it)
            })
    }

    private fun readSourceList() {
        val string = ContextProvider
            .getApplication()
            .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .getString(SP_KEY, "")
        if (!TextUtils.isEmpty(string)) {
            val type = object : TypeToken<List<PageSource>>() {}.type
            val list = Gson().fromJson<List<PageSource>>(string, type)
            sourceList.addAll(list)
        }
    }

    private fun saveSourceList() {
        val json = Gson().toJson(sourceList)
        ContextProvider
            .getApplication()
            .getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            .edit().putString(SP_KEY, json)
            .apply()
    }

    private fun buildSource(): Triple<PageSource, InputStream, InputStream> {
        val s = "{\"PageName\":\"xxxxxxx\",\"dsl\":{\"version\":\"1.0\",\"md5\":\"md5dsl\",\"url\":\"xxxxxx.kratos\"},\"code\":{\"version\":\"1.0\",\"md5\":\"md5code\",\"url\":\"xxxxxx.ktcode\"}}"
        val source = Gson().fromJson(s, PageSource::class.java)
        val dslStream = ContextProvider.getApplication().assets.open("login.json")
        val jsStream = ContextProvider.getApplication().assets.open("factorial.js")
        return Triple(source, dslStream, jsStream)
    }
}