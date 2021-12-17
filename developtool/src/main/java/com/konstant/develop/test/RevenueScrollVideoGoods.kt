package com.konstant.develop.test

import java.io.Serializable

data class RevenueScrollVideoGoods(
    val id: String? = "",    // 商品id
    val name: String? = "",  // 商品名称
    val image: String? = "", // 商品图片
    val page: String? = "",  // 售卖页面
    val index: String? = "", // 排序值
    val baseValue: String? = "",// 售卖价
    val tags: List<String>? = mutableListOf() // 标签
) : Serializable