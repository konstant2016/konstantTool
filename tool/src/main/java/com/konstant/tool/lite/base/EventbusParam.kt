package com.konstant.tool.lite.base

// 天气模块的参数状态发生变化 比如：用户选择了指定城市，用户删除了城市，用户添加了城市
class WeatherStateChanged(var cityNumChange: Boolean, var index: Int)

// 物流状态发生变化
class ExpressChanged

// 滑动返回状态发生变化
class SwipeBackStatus(var state: Int)

// 主题发生变化
class ThemeChanged

// 用户头像发生变化
class UserHeaderChanged

// 软件语言发生变化
class LanguageChanged

// 收藏开关发生了变化
class CollectionSettingChanged
