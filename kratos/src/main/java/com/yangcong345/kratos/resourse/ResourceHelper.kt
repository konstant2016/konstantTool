package com.yangcong345.kratos.resourse

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description 资源管理器，负责整个应用中DSL资源的管理
 * 1、整体资源列表管理
 * 2、预加载资源
 * 3、根据pageName返回单个资源
 * 4、资源校验
 * 5、资源缓存
 */
object ResourceHelper {

    /**
     * 根据单个DSL页面pageName获取DSL资源文件
     */
    fun getDSLPageResource(pageName: String, callback: PageResourceCallback) {
        CacheManager.getSourceBundle(pageName, callback)
    }

    /**
     * 预加载页面的JS与DSL
     */
    fun preloadPageResource(pageList: List<String>) {
        CacheManager.preloadSource(pageList)
    }

}