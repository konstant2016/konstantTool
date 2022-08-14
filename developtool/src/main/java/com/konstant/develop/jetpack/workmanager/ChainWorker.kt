package com.konstant.develop.jetpack.workmanager

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.*

/**
 * 时间：2022/8/14 20:49
 * 作者：吕卡
 * 备注：链式任务
 */

@SuppressLint("EnqueueWork")
object ChainWorker {

    class A(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            val data = Data.Builder().putString("data", "A任务的结果").build()
            return Result.success(data)
        }
    }

    class B(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            val string = inputData.getString("data")
            // string就是A传递过来的数据
            return Result.success()
        }
    }

    class C(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            return Result.success()
        }
    }

    class D(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            return Result.success()
        }
    }

    class E(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
        override fun doWork(): Result {
            return Result.success()
        }
    }

    /**
     * 创建链式任务
     * 注意：第一个任务失败时，第二个任务也不会继续走了
     */
    fun chainWorker(context: Context) {
        val a = OneTimeWorkRequest.Builder(A::class.java).build()
        val b = OneTimeWorkRequest.Builder(B::class.java).build()
        // 先执行a，再执行b
        WorkManager.getInstance(context).beginWith(a)
            .then(b)
            .enqueue()
    }

    /**
     * 同时执行
     */
    fun submitZipTask(context: Context){
        val a = OneTimeWorkRequestBuilder<A>().build()
        val b = OneTimeWorkRequestBuilder<B>().build()
        WorkManager.getInstance(context).beginWith(listOf(a,b)).enqueue()
    }

    /**
     * 混合链式任务
     * AB串行，CD串行，两个串之间并行，最后再执行e
     */
    fun buildCombineWorker(context: Context) {
        val a = OneTimeWorkRequest.Builder(A::class.java).build()
        val b = OneTimeWorkRequest.Builder(B::class.java).build()
        val c = OneTimeWorkRequest.Builder(C::class.java).build()
        val d = OneTimeWorkRequest.Builder(D::class.java).build()
        val e = OneTimeWorkRequest.Builder(E::class.java).build()

        val chain1 = WorkManager.getInstance(context).beginWith(a).then(b)
        val chain2 = WorkManager.getInstance(context).beginWith(c).then(d)

        WorkContinuation.combine(listOf(chain1,chain2)).then(e).enqueue()
    }

}