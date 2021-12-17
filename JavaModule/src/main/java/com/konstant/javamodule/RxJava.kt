package com.konstant.javamodule

import com.konstant.javamodule.function.FunctionOnErrorResumeNext
import com.konstant.javamodule.function.FunctionOnExceptionResumeNext
import com.konstant.javamodule.function.FunctionRetry

object RxJava {
    @JvmStatic
    fun main(args: Array<String>) {
        FunctionRetry().run()
    }
}