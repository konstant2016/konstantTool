package com.konstant.develop.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * 时间：2022/8/14 19:55
 * 作者：吕卡
 * 备注：一次性的任务提交
 */

object OneTimeWorker {

    class OneTimeWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            // 拿到输入的参数
            val string = inputData.getString("inputData") ?: ""
            Log.d("WorkManagerHelper", string)
            return Result.success() // 成功
            //return Result.failure() // 失败
            //return Result.retry() // 重试
        }

        // 不是一定要实现的方法
        override fun onStopped() {
            super.onStopped()
        }
    }

    /**
     * 创建只执行一次的任务
     *
     * 注意：退避策略需要配合Result.retry()
     * 如果直接返回了Result.failure()，则退避策略不会生效的
     */
    fun createOneTimeWork(context: Context) {
        val tag = "多个任务可以设置一个TAG，然后可以通过这一个TAG批量获取任务"
        val inputData = Data.Builder().putString("inputData", "这是传入的数据").build()
        val request = OneTimeWorkRequest.Builder(OneTimeWork::class.java)
            .setInitialDelay(10, TimeUnit.MICROSECONDS)  // 10毫秒后再执行
            .keepResultsForAtLeast(2, TimeUnit.DAYS)     // 任务至少会保留一天，WorkManager默认会保留一天的执行结果，可以通过这个变量修改执行结果默认的保存时间
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.MINUTES) // 设置退避策略，比如失败一次后，间隔10分钟后再试，再失败则间隔20分钟，30分钟，40分钟以此类推
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 2, TimeUnit.MINUTES)  //指数级增长，失败一次后，间隔10分钟，20分钟，40分钟，80分钟，160分钟。。。
            .setInputData(inputData)
            .addTag(tag)
            .build()
        val id = request.id
        WorkManager.getInstance(context).enqueue(request)
        // 根据任务TAG取消任务
        WorkManager.getInstance(context).cancelAllWorkByTag(tag)
        // 根据任务id取消任务
        WorkManager.getInstance(context).cancelWorkById(id)
    }

    // 监听执行的回调
    fun listenCallback(fragment: Fragment) {
        val inputData = Data.Builder().putString("inputData", "这是传入的数据").build()
        val request = OneTimeWorkRequest.Builder(OneTimeWork::class.java)
            .setInputData(inputData)
            .build()
        // 监听状态的回调
        WorkManager.getInstance(fragment.requireContext()).getWorkInfoByIdLiveData(request.id).observe(fragment) { workInfo ->
            val state = workInfo.state
        }
        // 获取当前的状态值
        val workInfo = WorkManager.getInstance(fragment.requireContext()).getWorkInfoById(request.id).get()
        workInfo.state
        // 记得要添加任务到WorkManger的队列中去
        WorkManager.getInstance(fragment.requireContext()).enqueue(request)
    }

}
