package com.konstant.tool.lite.module.weather.param

/**
 * 时间：2018/8/2 16:10
 * 作者：吕卡
 * 描述：描述:天气模块的参数状态发生变化 比如：用户选择了制定城市，用户删除了城市，用户添加了城市
 */
class WeatherStateChanged(var cityNumChange: Boolean, var index: Int)