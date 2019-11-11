package com.github.danzx.xof.common

data class SortSpec(
    val property: String,
    val direction: Direction) {

    enum class Direction {
        ASC, DESC
    }
}

class SortSpecListBuilder {

    private val list = mutableListOf<SortSpec>()

    fun build(): List<SortSpec> = list

    operator fun String.unaryPlus() {
        list += SortSpec(this, SortSpec.Direction.ASC)
    }

    operator fun String.unaryMinus() {
        list += SortSpec(this, SortSpec.Direction.DESC)
    }
}

fun sortBy(actions: SortSpecListBuilder.() -> Unit): List<SortSpec> {
    val builder = SortSpecListBuilder()
    builder.actions()
    return builder.build()
}
