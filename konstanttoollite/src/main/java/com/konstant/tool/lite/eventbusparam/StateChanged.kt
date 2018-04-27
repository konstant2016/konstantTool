package com.konstant.tool.lite.eventbusparam

// 描述:天气模块的参数状态发生变化 比如：用户选择了制定城市，用户删除了城市，用户添加了城市
class WeatherStateChanged(var cityNumChange: Boolean, var index: Int)

// 滑动返回的状态发生变化
class SwipeBackState(var state: Boolean)

// 标题发生变化
class TitleChanged(var title: String)

// 主题发生变化
class ThemeChanged

// 物流状态发生变化
class ExpressChanged