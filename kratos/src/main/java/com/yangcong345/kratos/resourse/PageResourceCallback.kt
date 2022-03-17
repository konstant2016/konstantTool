package com.yangcong345.kratos.resourse

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description 资源回调，获取单个DSL页面资源回调
 *
 */
interface PageResourceCallback {

    fun onStart(pageName:String)

    fun onProgress(progress : Int)

    fun onFail(e:Throwable)

    fun onSuccess(bundle: DSLBundle)

}