package com.konstant.develop.jetpack.workmanager

import android.content.Context
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * 时间：2022/8/14 20:14
 * 作者：吕卡
 * 备注：周期性的任务提交
 */

object PeriodicWorker {

    class PeriodicWork(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

        override fun doWork(): Result {
            return Result.success()
        }
    }

    /**
     * 周期性任务，最低时间间隔为15分钟，如果设置的低于15分钟，系统会默认给你设置为 15分钟
     * 代码逻辑看这里：WorkSpec.setPeriodic()
     */
    fun submitPeriodicWorker(fragment: Fragment) {
        // 周期性的任务，每次间隔30分钟
        val request = PeriodicWorkRequest.Builder(PeriodicWork::class.java, 30, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(fragment.requireContext()).enqueue(request)
    }

    /**
     * 设置约束
     * 如果当前不满足约束条件，则这个任务会处于挂起状态，等待满足约束条件时才会触发
     */
    fun buildConstraintsWorker(fragment: Fragment) {
        val config = Constraints.Builder()
            .setRequiresDeviceIdle(true)    // 是否设备空闲时触发
            .setRequiresCharging(true)      // 是否要求充电时触发
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)   // 对网络状态的现实策略
            .setRequiresBatteryNotLow(true) // 要求设备不需要处于低电量（通常是20%，设备厂商设置的）
            .setRequiresStorageNotLow(true) // 要求设备空间充足
            .addContentUriTrigger(Uri.parse(""), false) // 监听URI发生变化，比如在图库里面插入了一张图片
            .build()
        val request = PeriodicWorkRequestBuilder<OneTimeWorker.OneTimeWork>(24, TimeUnit.SECONDS)
            .setConstraints(config)
            .build()
        WorkManager.getInstance(fragment.requireContext()).enqueue(request)
    }
}