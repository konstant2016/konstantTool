package com.konstant.develop.tree

import java.io.Serializable


data class Response(
    val nodes: List<Node>,
    val relation: List<Relation>
) : Serializable

data class Node(
    val x: Int,
    val y: Int,
    val id: String
) : Serializable

data class Relation(
    val endId: String,
    val startId: String
) : Serializable
