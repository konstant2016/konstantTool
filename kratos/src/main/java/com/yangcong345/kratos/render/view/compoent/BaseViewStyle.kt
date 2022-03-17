package com.yangcong345.kratos.render.view.compoent

import java.io.Serializable

open class BaseViewStyle(
    var backgroundColor: String? = "",
    var alpha: Float? = 1f,
    var borderColor: String? = "",
    var borderWidth: Float? = 0f,
    var cornerRadius: Float? = 0f,
    var display: Boolean? = false
):Serializable