package com.github.danzx.xof.core.util

data class SortSpec(
    val property: String,
    val direction: Direction) {

    enum class Direction {
        ASC, DESC
    }
}
