package com.konstant.develop.jetpack.workmanager

/**
 * 时间：2022/8/14 21:32
 * 作者：吕卡
 * 踩坑：
 *  1、应用被杀死后无法执行的，当应用重新启动后，之前提交的work会被激活，这个时候需要判断任务是否已经添加到队列中，避免重复添加
 *  2、应用启动后，由于task会被立即激活，而任务中需要依赖的某些参数可能还没有初始化，这个时候需要判空
 *  3、Work的激活时机在Application的onCreate之后
 */

object WorkManagerError {
}