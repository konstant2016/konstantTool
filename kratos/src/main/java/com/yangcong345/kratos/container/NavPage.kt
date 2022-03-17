package com.yangcong345.kratos.container

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description 页面跳转参数封装类，可扩展一些页面转场动画或自定义操作
 */
data class NavPage (

    var pageName:String = "",

    var pageParams:HashMap<String,Any> = hashMapOf()

)