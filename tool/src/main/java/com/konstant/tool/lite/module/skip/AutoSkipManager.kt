package com.konstant.tool.lite.module.skip

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

object AutoSkipManager {

    private const val SKIP_TOAST = "autoSkipToast"
    private const val SKIP_MATCH = "autoSkipMatch"
    private const val SKIP_RULES = "autoSkipRules"
    private const val SKIP_WHITE_LIST = "autoWhiteList"
    private const val SKIP_DIALOG_SHOW = "skipDialogShow"
    private val mCustomRules = mutableListOf<CustomRule>()
    private val mWhitList = mutableListOf<String>()

    data class CustomRule(val packageName: String, val className: String, val resourceId: String)

    fun onCreate(context: Context) {
        val rulesTemp = FileUtil.readFileFromFile(context, SKIP_RULES)
        if (rulesTemp.isNotEmpty()) {
            val array = Gson().fromJson<List<CustomRule>>(String(rulesTemp), object : TypeToken<List<CustomRule>>() {}.type)
            mCustomRules.addAll(array)
        }
        val whiteListTemp = FileUtil.readFileFromFile(context, SKIP_WHITE_LIST)
        if (rulesTemp.isNotEmpty()) {
            val array = Gson().fromJson<List<String>>(String(whiteListTemp), object : TypeToken<List<String>>() {}.type)
            mWhitList.addAll(array)
        }
    }

    fun onDestroy(context: Context) {
        val rulesJson = Gson().toJson(mCustomRules)
        FileUtil.saveFileToFile(context, SKIP_RULES, rulesJson.toByteArray())

        val whiteListJson = Gson().toJson(mWhitList)
        FileUtil.saveFileToFile(context, SKIP_WHITE_LIST, whiteListJson.toByteArray())
    }

    // 跳过提示
    fun setShowToast(context: Context, show: Boolean) {
        FileUtil.saveDataToSp(context, SKIP_TOAST, show)
    }

    fun getShowToast(context: Context): Boolean {
        return FileUtil.readDataFromSp(context, SKIP_TOAST, false)
    }

    // 模糊匹配
    fun setMatch(context: Context, match: Boolean) {
        FileUtil.saveDataToSp(context, SKIP_MATCH, match)
    }

    // 是否启用模糊匹配
    fun getMatch(context: Context): Boolean {
        return FileUtil.readDataFromSp(context, SKIP_MATCH, false)
    }

    // 自定义规则
    fun getCustomRules(): List<CustomRule> {
        return mCustomRules
    }

    fun addCustomRules(customRule: CustomRule) {
        mCustomRules.add(customRule)
    }

    fun removeRules(customRule: CustomRule) {
        mCustomRules.remove(customRule)
    }

    // 应用白名单
    fun getAppWhiteList(): List<String> {
        return mWhitList
    }

    fun addAppIntoWhiteList(list: List<String>) {
        mWhitList.clear()
        mWhitList.addAll(list)
    }

    // 是否显示实验性功能的提示
    fun showDialogTips(context: Context): Boolean {
        return FileUtil.readDataFromSp(context, SKIP_DIALOG_SHOW, true)
    }

    fun setShowDialogTips(context: Context, show: Boolean) {
        FileUtil.saveDataToSp(context, SKIP_DIALOG_SHOW, show)
    }

}