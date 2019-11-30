package com.github.danzx.xof.common.sort.dsl

import com.github.danzx.xof.common.sort.SortSpec

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
