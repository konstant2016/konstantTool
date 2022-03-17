package com.yangcong345.kratos.render

import com.google.android.flexbox.*
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.yangcong345.kratos.utils.GsonUtil
import java.io.Serializable
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class RenderStyle(
    val viewId: String,
    val layoutStyle: LayoutStyle?,
    val viewStyle: Map<String, Any>?,// 不同的控件，属性不一样，因此这里用String来接收，再解析为控件具体对应的属性
    val viewAction: List<String>?,
    val dataSource: Map<String, Any>?,     // 控件需要用到的数据，JS会传过来
    val viewType: String?,       // 用来区分不同的VIEW类型
    val children: List<RenderStyle>?
) : Serializable {

    fun <T> getViewStyle(clazz: Class<T>): T? {
        if (viewStyle == null) return null
        val dataStr= GsonUtil.safeGson.toJson(viewStyle)
        return GsonUtil.safeGson.fromJson(dataStr, clazz)
    }

    fun <T> getDataSource(clazz: Class<T>): T? {
        if (dataSource == null) return null
        return Gson().fromJson(Gson().toJson(dataSource), clazz)
    }

    fun <T> getDataSource(clazz: List<T>): List<T> {
        if (dataSource == null) return emptyList()
        val type = object : TypeToken<T>() {}.type
        return Gson().fromJson(Gson().toJson(dataSource), type)
    }

}

data class Side(
    val left: String,
    val top: String,
    val right: String,
    val bottom: String,
) : Serializable

data class LayoutStyle(
    // 以下的5个为设置到父控件的属性
    val flexDirection: String?,
    val flexWrap: String?,
    val justifyContent: String?,
    val alignItems: String?,
    val alignContent: String?,

    // 以下的几个为设置到子控件的属性
    val flexGrow: Float?,
    val flexShrink: Float?,
    val flexBasisPercent: Float?,
    val alignSelf: String?,
    val margin: Side?,
    val padding: Side?,
    val maxValue: List<String>?,
    val minValue: List<String>?,
    val aspectRatio: Float?,
    val layoutWidth: String,
    val layoutHeight: String,
    val position: String?,
) : Serializable {

    fun getFlexDirection() = when (flexDirection) {
        "column" -> FlexDirection.COLUMN
        "columnReverse" -> FlexDirection.COLUMN_REVERSE
        "row" -> FlexDirection.ROW
        "rowReverse" -> FlexDirection.ROW_REVERSE
        else -> PARAM_UN_KNOW
    }

    fun getFlexWrap() = when (flexWrap) {
        "noWrap" -> FlexWrap.NOWRAP
        "wrap" -> FlexWrap.WRAP
        "wrapReverse" -> FlexWrap.WRAP_REVERSE
        else -> PARAM_UN_KNOW
    }

    fun getJustifyContent() = when (justifyContent) {
        "start" -> JustifyContent.FLEX_START
        "end" -> JustifyContent.FLEX_END
        "center" -> JustifyContent.CENTER
        "spaceBetween" -> JustifyContent.SPACE_BETWEEN
        "spaceAround" -> JustifyContent.SPACE_AROUND
        "spaceEvenly" -> JustifyContent.SPACE_EVENLY
        else -> PARAM_UN_KNOW
    }

    fun getAlignItems() = when (alignItems) {
        "stretch" -> AlignItems.STRETCH
        "start" -> AlignItems.FLEX_START
        "end" -> AlignItems.FLEX_END
        "center" -> AlignItems.CENTER
        "baseline" -> AlignItems.BASELINE
        else -> PARAM_UN_KNOW
    }

    fun getAlignContent() = when (alignContent) {
        "start" -> AlignContent.FLEX_START
        "end" -> AlignContent.FLEX_END
        "center" -> AlignContent.CENTER
        "stretch" -> AlignContent.STRETCH
        "spaceBetween" -> AlignContent.SPACE_BETWEEN
        "spaceAround" -> AlignContent.SPACE_AROUND
        else -> PARAM_UN_KNOW
    }

    fun getAlignSelf() = when (alignSelf) {
        "auto" -> AlignSelf.AUTO
        "stretch" -> AlignItems.STRETCH
        "start" -> AlignItems.FLEX_START
        "end" -> AlignItems.FLEX_END
        "center" -> AlignItems.CENTER
        "baseline" -> AlignItems.BASELINE
        else -> PARAM_UN_KNOW
    }

}

const val PARAM_UN_KNOW = -100
