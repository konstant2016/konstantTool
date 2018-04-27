package com.konstant.tool.lite.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 描述:线程池管理器
 * 创建人:菜籽
 * 创建时间:2018/4/24 上午11:12
 * 备注:
 */

object ThreadManager {

    fun getThreadPool(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    fun test(){
        getThreadPool().execute {
            print("这里执行的就是线程内的任务啦")
        }
    }

}