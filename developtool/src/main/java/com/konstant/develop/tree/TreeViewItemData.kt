package com.konstant.develop.tree

import java.io.Serializable


data class Response(
    val nextList: List<Response>?,
    val x: Int,
    val y: Int
) : Serializable
