package com.konstant.tool.lite.module.user

import java.io.Serializable

data class UserInfo(
        val account: String = "",         // 用户的账号
        val stockList: String = "",       // 股票列表
        val stockHistory: String = "",    // 股票历史价格
        val weatherCityList: String = "", // 天气中的城市列表
        val collection: String = ""       // 用户的功能收藏列表
) : Serializable