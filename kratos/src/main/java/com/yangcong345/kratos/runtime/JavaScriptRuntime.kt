package com.yangcong345.kratos.runtime

import android.os.Build
import android.os.Handler
import android.os.Looper
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.utils.MemoryManager
import com.yangcong345.kratos.utils.FileUtil
import java.lang.IllegalArgumentException
import kotlin.concurrent.thread

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description js执行环境，内部维护独立的V8Runtime
 */
class JavaScriptRuntime {

    private lateinit var runtime : V8
    private lateinit var memoryManager: MemoryManager

    companion object{
        fun createRuntime(): JavaScriptRuntime {
            return JavaScriptRuntime()
        }
    }

    private lateinit var looper: Looper
    private lateinit var mThreadHandler:Handler
    init {
        thread {
            Looper.prepare()
            looper = Looper.myLooper()!!
            initRuntime()
            Looper.loop()
        }
    }

    private fun initRuntime(){
        mThreadHandler = Handler(looper)
        runtime = V8.createV8Runtime()
        memoryManager = MemoryManager(runtime)
    }

    fun executeScript(scripts:List<String>,callback: JsFileExecuteCallback){
        mThreadHandler.post {
            try {
                scripts.forEach {
                    val stringFromFile = FileUtil.getStringFromFile(it)
                    runtime.executeScript(stringFromFile)
                }
                callback.onSuccess()
            }catch (t:Throwable){
                callback.onFail(t)
            }
        }
    }


    fun registerJavaMethod(methodName:String,callback:(String)->Unit){
        if(methodName.isEmpty()){
            throw IllegalArgumentException("methodName must not be empty")
        }
        mThreadHandler.post {
            if(runtime.isReleased){
                return@post
            }
            runtime.registerJavaMethod({ _, paramsArray ->
                val responseData = paramsArray.getString(0)
                callback.invoke(responseData)
            },methodName)
        }

    }


    fun executeJSFunction(functionName:String,parameter:String? = null){
        mThreadHandler.post {
            if(runtime.isReleased){
                return@post
            }
            runtime.executeJSFunction(functionName,parameter)
        }
    }

    /**
     * 释放runtime资源占用
     */
    fun onClose(){
        if(runtime.isReleased){
            return
        }
        mThreadHandler.post {
            if(runtime.isReleased){
                return@post
            }
            runtime.close()
            memoryManager.release()
            mThreadHandler.removeCallbacksAndMessages(null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                looper.quitSafely()
            }else{
                looper.quit()
            }
        }
    }

}