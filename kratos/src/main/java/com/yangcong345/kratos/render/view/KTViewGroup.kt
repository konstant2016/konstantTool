package com.yangcong345.kratos.render.view

import com.yangcong345.kratos.render.RenderStyle
import com.yangcong345.kratos.render.ViewEventListener

interface KTView {
    fun updateViewStyle(renderStyle: String)
    fun updateViewStyle(renderStyle: RenderStyle)
    fun setController(controller: ViewEventListener)
}

interface KTViewGroup : KTView {
    fun insertView(view: KTView)
}