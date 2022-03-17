package com.yangcong345.kratos.bridge

import com.yangcong345.kratos.runtime.JavaScriptRuntime

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description dsl和js执行之间的桥
 * 1、支持动态加载插件 一期
 * 2、通用插件注册 一期
 * 3、View插件：一期 两个插件
 *         1）负责将view事件（点击或触摸等）传递给JS
 *         2）负责将js更改view的数据传递给DSLRenderManger
 * 4、页面生命周期插件 一期
 * 5、桥接RIKI增强 二期
 *
 */
class DSLJsBridge() {


    private fun initCommonPlugin(){

    }

}