package com.konstant.tool.lite.module.express.activity

import com.konstant.tool.lite.module.express.server.ExpressData
import com.konstant.tool.lite.network.NetService

/**
 * 时间：2019/4/25 15:17
 * 创建：吕卡
 * 描述：物流查询的工具类
 */

object ExpressPresenter {

    interface ExpressResult {
        fun onSuccess(response: ExpressData)
        fun onError()
    }

    fun getExpressDetail(expressNo: String, result: ExpressResult) {
        NetService.expressQuery(expressNo) { response ->
            if (response.status == 1) {
                val list = arrayListOf<ExpressData.Message>()
                response.data.messages.forEach { message ->
                    list.add(ExpressData.Message(message.context, message.time))
                }
                val data = ExpressData(response.data.company, response.data.tel, response.data.status, response.data.nu, list)
                result.onSuccess(data)
            } else {
                result.onError()
            }
        }
    }


}