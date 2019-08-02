package com.konstant.tool.lite.module.weather.param

// 天气模块的参数状态发生变化 比如：用户选择了制定城市，用户删除了城市，用户添加了城市
class WeatherStateChanged(var cityNumChange: Boolean, var index: Int)

// 主标题发生了变化
class TitleChanged(val title:String)

// 副标题发生了变化
class SubTitleChanged(val subTitle:String)