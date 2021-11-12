package com.konstant.develop

import android.net.Uri
import android.text.TextUtils
import java.lang.StringBuilder

/**
 * @Data 2019-11-26 11:16
 * @author wjt
 * @Description uri 相关工具类
 * @version 1.0
 */
object UriUtil {

    /**
     * 给url 添加指定 map集合的query
     * NOTE: 如果 url 中已经存在与map中相同的key，则key对应的value会添加会失效。如果需要替换，应该使用 [addOrReplaceQuery]
     * @param url String 原始的需要添加query的url
     * @param map Map<String, Any>  需要添加的query集合
     * @return String 添加指定query以后的url
     */
    @JvmStatic
    fun addQuery(url: String, map: Map<String, Any?>): String {
        var newUrl = url
        map.forEach { (key, value) ->
            newUrl = addQuery(newUrl, key, value)
        }
        return newUrl
    }

    /**
     * 给url 添加指定 map集合的query
     * NOTE: 如果 url 中已经存在与map中相同的key，则key对应的value会替换原来的value。否者执行普通添加。
     * @param url String 原始的需要添加query的url
     * @param map Map<String, Any>  需要添加的query集合
     * @return String 添加指定query以后的url
     */
    @JvmStatic
    fun addOrReplaceQuery(url: String, map: Map<String, Any?>): String {
        var newUrl = url
        map.forEach { (key, value) ->
            newUrl = addOrReplaceQuery(newUrl, key, value)
        }
        return newUrl
    }

    /**
     *  如果 url 中已经存在指定的key，则添加的value会覆盖原有值；否者执行普通添加逻辑。
     * @param url String 原始的需要添加或替换query的url
     * @param key String 需要添加或替换的query key
     * @param value Any 需要添加或替换的query value
     * @return String 添加或替换指定query以后的url
     */
    fun addOrReplaceQuery(url: String, key: String, value: Any?): String {
        val queryContainsKey = isQueryContainsKey(url, key)
        return if (queryContainsKey) {
            replaceQuery(url, key, value)
        } else {
            addQuery(url, key, value)
        }
    }

    /**
     * 替换 url内指定的key对应的value
     * @param url String 需要替换的url
     * @param key String 需要替换的key
     * @param value Any? 需要替换的value
     * @return String 替换后的url
     */
    private fun replaceQuery(url: String, key: String, value: Any?): String {
        val builder = StringBuilder()
        val baseUrl = if (url.contains("?")) {
            url.split("?")[0]
        } else {
            url
        }
        builder.append(baseUrl)
        val oldUri = Uri.parse(url)
        val queryParameterNames = oldUri.queryParameterNames
        queryParameterNames.forEachIndexed { index, eachKey ->
            val newValue = if (eachKey == key) {
                value?.toString() ?: ""
            } else {
                oldUri.getQueryParameter(eachKey)
            }
            if (index == 0) {
                builder.append("?").append(eachKey).append("=").append(newValue)
            } else {
                builder.append("&").append(eachKey).append("=").append(newValue)
            }
        }
        return builder.toString()
    }

    /**
     * 给url添加指定 key value的query
     * NOTE: 如果 url 中已经存在指定的key，则本次添加会失效。如果需要替换，应该使用 [addOrReplaceQuery]
     * @param url String 原始的需要添加query的url
     * @param key String 需要添加的query key
     * @param value Any 需要添加的query value
     * @return String 添加指定query以后的url
     */
    @JvmStatic
    fun addQuery(url: String, key: String, value: Any?): String {
        val containsKey = isQueryContainsKey(url, key)
        if (containsKey) return url
        val builder = StringBuilder()
        builder.append(url)
        if (url.contains("?")) {
            builder.append("&").append(key).append("=").append(value)
        } else {
            builder.append("?").append(key).append("=").append(value)
        }
        return builder.toString()
    }

    /**
     * 判断当前url 的query 是否包含指定的key
     * @param url String 需要判断的url
     * @param key String 需要判断的key
     * @return Boolean 包含指定key 返回 true；否则返回false。
     */
    @JvmStatic
    fun isQueryContainsKey(url: String, key: String): Boolean {
        return try {
            Uri.parse(url).queryParameterNames.contains(key)
        } catch (e: Exception) {
            false
        }
    }
}