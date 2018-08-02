package com.konstant.tool.lite.module.express.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

/**
 * 描述:本地物流信息的管理工具
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午7:48
 * 备注:
 */


object ExpressManager {

    private val NAME_LOCAL_EXPRESS = "localExpress"
    private val mExpressList = ArrayList<ExpressData>()

    // 初始化的时候，读取本地保存的数据
    fun onCreate(context: Context) {
        val temp = FileUtil.readFileFromFile(context, NAME_LOCAL_EXPRESS)
        if (temp.isNotEmpty()) {
            val array = JSON.parseArray(String(temp), ExpressData::class.java)
            mExpressList.addAll(array)
        }
    }

    // APP退出的时候，把数据保存到本地
    fun onDestroy(context: Context) {
        val json = JSON.toJSONString(mExpressList)
        Executors.newSingleThreadExecutor().execute {
            FileUtil.saveFileToFile(context, NAME_LOCAL_EXPRESS, json.toByteArray())
        }
    }

    // 对外提供的接口
    fun readExpress(): List<ExpressData> = mExpressList


    //更新物流数据
    fun updateExpress(orderNo: String, company: String?, remark: String?, state: String?) {
        mExpressList.forEach { express ->
            if (express.orderNo == orderNo) {
                company?.let { express.company = company }
                remark?.let { express.remark = remark }
                state?.let { express.state = state }
            }
        }
    }

    // 删除物流数据
    fun deleteExpress(data: ExpressData? = null, orderNo: String = "") {
        var exp = ExpressData()
        if (data != null) {
            exp = data
        }
        if (orderNo != ""){
            exp.orderNo = orderNo
        }
        mExpressList.remove(exp)
    }

    // 添加新的物流
    fun addExpress(orderNo: String, company: String?, remark: String?, state: String?) {
        val expressData = ExpressData(company, orderNo, remark
                ?: "保密物件", state ?: "暂无信息")
        if (!mExpressList.contains(expressData)) mExpressList.add(expressData)
    }

}