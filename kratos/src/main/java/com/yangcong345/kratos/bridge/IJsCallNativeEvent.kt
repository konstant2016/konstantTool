package com.yangcong345.kratos.bridge

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description Js call native的事件处理
 * onKTReceiveJSCallEvent
    入参列表
    参数1：eventName  事件名称（字符串）
    参数2：requestParam  事件参数（字典）
 */
interface IJsCallNativeEvent {

    fun onJsCallNativeEvent(eventName:String,requestParam:String)

}