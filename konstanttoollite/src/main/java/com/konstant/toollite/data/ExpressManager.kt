package com.konstant.toollite.data

import android.content.Context
import com.alibaba.fastjson.JSON
import com.konstant.toollite.util.NameConstant
import com.konstant.toollite.util.FileUtils

/**
 * 描述:本地物流信息的管理工具
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午7:48
 * 备注:
 */


object ExpressManager {

    // 读取本地保存的物流数据
    fun readExpress(context: Context): List<ExpressData> {
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS)
        val list = ArrayList<ExpressData>()
        if (s != null) {
            val array = JSON.parseArray(s, ExpressData::class.java)
            list.addAll(array)
        }
        return list
    }

    //更新物流数据
    fun updateExpress(context: Context, orderNo: String, company: String?, remark: String?, state: String?) {
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS)
                ?: return
        val list = JSON.parseArray(s, ExpressData::class.java)
        list.forEach {
            if (it.orderNo == orderNo) {
                if ((company != null)) {
                    it.company = company
                }
                if (remark != null) {
                    it.remark = remark
                }
                if ((state != null)) {
                    it.state = state
                }
            }
        }
        val json = JSON.toJSONString(list)
        FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS, json)
    }

    // 删除物流数据
    fun deleteExpress(context: Context, orderNo: String) {
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS)
                ?: return
        val list = JSON.parseArray(s, ExpressData::class.java)
        var expre: ExpressData? = null
        list.forEach {
            if (it.orderNo == orderNo) {
                expre = it
            }
        }
        list.remove(expre)
        val json = JSON.toJSONString(list)
        FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS, json)
    }

    // 添加新的物流
    fun addExpress(context: Context, orderNo: String, company: String?, remark: String?, state: String?) {
        val list = ArrayList<ExpressData>()
        val s = FileUtils.readDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS)
        if (s != null) {
            val array = JSON.parseArray(s, ExpressData::class.java)
            list.addAll(array)
        }
        list.forEach { if (it.orderNo == orderNo) return }
        list.add(ExpressData(company, orderNo, remark ?: "保密物件", state ?: "暂无信息"))
        val json = JSON.toJSONString(list)
        FileUtils.saveDataWithSharedPreference(context, NameConstant.NAME_LOCAL_EXPRESS, json)
    }

}