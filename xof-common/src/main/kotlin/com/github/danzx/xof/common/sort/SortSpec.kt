package com.github.danzx.xof.common.sort

data class SortSpec(
    val property: String,
    val direction: Direction) {

    enum class Direction {
        ASC, DESC
    }
}
