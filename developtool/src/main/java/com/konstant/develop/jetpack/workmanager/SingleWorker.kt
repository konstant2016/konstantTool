package com.konstant.develop.jetpack.workmanager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.work.*

/**
 * 时间：2022/8/14 20:37
 * 作者：吕卡
 * 备注：创建唯一任务
 */

object SingleWorker {

    class SingleWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            return Result.success()
        }
    }

    /**
     * 创建唯一任务
     * ExistingWorkPolicy.REPLACE：如果队列之前有任务，直接干掉，传入新的
     * ExistingWorkPolicy.KEEP：如果之前有任务还在，本次插入无效
     * ExistingWorkPolicy.APPEND：拼到上一个任务的后面
     */
    fun buildSingleWorker(fragment: Fragment) {
        val singleTaskName = "唯一的任务id"
        val request = OneTimeWorkRequest.Builder(OneTimeWorker.OneTimeWork::class.java).build()
        // 创建唯一任务
        WorkManager.getInstance(fragment.requireContext()).beginUniqueWork(singleTaskName, ExistingWorkPolicy.REPLACE, request).enqueue()
        WorkManager.getInstance(fragment.requireContext()).enqueueUniqueWork(singleTaskName, ExistingWorkPolicy.REPLACE, request)

        // 监听这个唯一任务,根据任务名称来监听
        WorkManager.getInstance(fragment.requireContext()).getWorkInfosForUniqueWorkLiveData(singleTaskName).observe(fragment){ workInfoList->
            workInfoList.forEach {
                // 这里可以根据任务的id，来获取指定的任务
            }
        }
    }

}