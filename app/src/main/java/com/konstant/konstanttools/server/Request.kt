package com.konstant.konstanttools.server

/**
 * 描述:网络访问的请求体
 * 创建人:菜籽
 * 创建时间:2017/12/29 上午11:40
 * 备注:
 */

// 查询手机号码归属地
data class PhoneLocationRequest(val phone: String, val key: String)

// 查询身份证信息
data class IDNumber(val cardno: String, val key: String)

// 查询天气
data class WeatherRequest(val location: String, val key: String)

// 翻译
data class TranslateRequest(val q: String, val from: String, val to: String, val appid: String, val salt: Int, val sign: String)