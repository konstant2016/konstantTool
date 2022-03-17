package com.yangcong345.kratos.runtime

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description
 */
interface JsFileExecuteCallback {

    fun onSuccess()

    fun onFail(t:Throwable)
}