package com.konstant.tool.lite.module.user

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.konstant.tool.lite.base.BaseActivity
import com.konstant.tool.lite.main.FunctionCollectorManager
import com.konstant.tool.lite.module.stock.StockManager
import com.konstant.tool.lite.module.weather.data.CountryManager
import com.konstant.tool.lite.network.NetworkHelper
import com.konstant.tool.lite.util.FileUtil

object UserInfoSyncHelper {

    /**
     * 把用户的信息上传到服务器
     */
    fun uploadUserInfo(activity: BaseActivity) {
        val account = FileUtil.readDataFromSp(activity, "account", "")
        if (account.isEmpty()) return
        val stockList = Gson().toJson(StockManager.getStockHistory())
        val stockHistory = Gson().toJson(StockManager.getStockList())
        val weatherCityList = Gson().toJson(CountryManager.readLocalCityList())
        val collection = Gson().toJson(FunctionCollectorManager.getCollectionFunction())
        val userInfo = UserInfo(account, stockList, stockHistory, weatherCityList, collection)
        activity.showLoading(true)
        val dispose = NetworkHelper.uploadUserInfo(userInfo)
                .subscribe({
                    activity.showLoading(false)
                    activity.showToast("上传成功")
                }, {
                    activity.showToast("上传失败，请稍候重试")
                })
    }

    /**
     * 获取服务器的信息并保存到本地
     */
    fun getAndSaveUserInfo(activity: BaseActivity) {
        val dispose = NetworkHelper.getUserInfo()
                .map {
                    val stockList = it.stockList
                    StockManager.saveSyncStockList(activity, stockList)
                    val stockHistory = it.stockHistory
                    StockManager.saveSyncStockHistory(activity, stockHistory)
                    val weatherCityList = it.weatherCityList
                    CountryManager.saveSyncCountryList(activity, weatherCityList)
                    val collection = it.collection
                    FunctionCollectorManager.saveSyncCollection(activity, collection)
                }.subscribe({
                    activity.showToast("同步成功")
                }, {
                    activity.showToast("获取失败，请稍候重试")
                })
    }

    fun isUserLogin(context: Context): Boolean {
        val account = FileUtil.readDataFromSp(context, "account", "")
        return !TextUtils.isEmpty(account)
    }

}