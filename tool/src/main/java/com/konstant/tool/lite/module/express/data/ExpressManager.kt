package com.konstant.tool.lite.module.express.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.tool.lite.module.express.server.ExpressData
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
    fun updateExpress(number: String, company: String?, name: String?, status: String?) {
        mExpressList.forEach { express ->
            if (express.number == number) {
                company?.let { express.company = company }
                name?.let { express.name = name }
                status?.let { express.status = status }
            }
        }
    }

    // 删除物流数据
    fun deleteExpress(data: ExpressData? = null, number: String = "") {
        var exp = ExpressData()
        if (data != null) {
            exp = data
        }
        if (number != "") {
            exp.number = number
        }
        mExpressList.remove(exp)
    }

    // 添加新的物流
    fun addExpress(number: String, company: String = "物流单号", status: String = "暂无信息", name: String = "保密物件") {
        val expressData = ExpressData(company, number, status, name)
        if (!mExpressList.contains(expressData)) mExpressList.add(expressData)
    }

}