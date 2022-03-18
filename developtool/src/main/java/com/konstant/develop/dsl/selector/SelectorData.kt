package com.konstant.develop.dsl.selector

data class SelectorData(
    val id: Int,            //
    val name: String,       // 数学
    val stages: List<Stage> // 小学、初中、高中
)

data class Stage(
    var selected: Boolean,
    val id: Int,
    val name: String,       // 小学
    val publishers: List<Publisher>
)

data class Publisher(
    var selected: Boolean,
    val fullName: String,
    val id: Int,
    val name: String,
    val semesters: List<Semester>
)

data class Semester(
    var selected: Boolean,
    val id: Int,
    val includeLadder: Boolean,
    val includePractice: Boolean,
    val name: String
)