package com.konstant.tool.lite.module.wxfake

data class Conversion(val msg: String, // 对话信息
                      val type: Int,    // 类型，对方，我方，时间
                      val fileName: String  // 头像名字，用于查询并展示有头像
)

object ConversionType {
    const val TYPE_ADVERSE = 0x01
    const val TYPE_MINE = 0x02
    const val TYPE_TIME = 0x03
}