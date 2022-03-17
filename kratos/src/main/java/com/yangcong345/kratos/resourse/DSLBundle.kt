package com.yangcong345.kratos.resourse

import java.io.File
import java.io.Serializable

/**
 *@author xiaodong
 *@date 2022/3/8
 *@description 已经下载完并经过完整性校验的本地资源地址
 */
data class DSLBundle(
    var pageName: String,
    var dslFile: File,
    var jsFile: List<File>
) : Serializable



