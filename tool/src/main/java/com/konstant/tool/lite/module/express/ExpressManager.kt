package com.konstant.tool.lite.module.express

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.konstant.tool.lite.R
import com.konstant.tool.lite.base.KonApplication
import com.konstant.tool.lite.data.bean.express.ExpressData
import com.konstant.tool.lite.util.FileUtil
import java.util.concurrent.Executors

/**
 * 描述:本地物流信息的管理工具
 * 创建人:菜籽
 * 创建时间:2018/4/5 下午7:48
 * 备注:
 */

object ExpressManager {

    private const val NAME_LOCAL_EXPRESS = "localExpress"
    private const val NAME_EXPRESS_DIALOG = "showExpressDialog"
    private val mExpressList = ArrayList<ExpressData>()

    // 初始化的时候，读取本地保存的数据
    fun onCreate(context: Context) {
        val temp = FileUtil.readFileFromFile(context, NAME_LOCAL_EXPRESS)
        if (temp.isNotEmpty()) {
            val array = Gson().fromJson<List<ExpressData>>(String(temp), object : TypeToken<List<ExpressData>>() {}.type)
            mExpressList.addAll(array)
        }
    }

    // APP退出的时候，把数据保存到本地
    fun onDestroy(context: Context) {
        val json = Gson().toJson(mExpressList)
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
    fun addExpress(number: String, company: String? = KonApplication.context.resources.getString(R.string.express_order_number), status: String? = KonApplication.context.resources.getString(R.string.express_empty_state), name: String? = KonApplication.context.resources.getString(R.string.express_name_unknown)) {
        val expressData = ExpressData(company, number, status, name)
        if (!mExpressList.contains(expressData)) mExpressList.add(expressData)
    }

    // 是否显示弹窗提示
    fun showDialog(context: Context): Boolean {
        return FileUtil.readDataFromSp(context, NAME_EXPRESS_DIALOG, true)
    }

    fun setShowDialog(context: Context, show: Boolean) {
        FileUtil.saveDataToSp(context, NAME_EXPRESS_DIALOG, show)
    }

}