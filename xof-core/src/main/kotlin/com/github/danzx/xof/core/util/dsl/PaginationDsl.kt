package com.github.danzx.xof.core.util.dsl

import com.github.danzx.xof.core.util.Pagination

object page

class PaginationBuilder {
    private var size: Int? = null
    private var number: Int? = null

    infix fun page.size(value: Int?) {
        size = value
    }

    infix fun page.number(value: Int?) {
        number = value
    }

    fun build() = Pagination(
        size ?: Pagination.DEFAULT_PAGE_SIZE,
        number ?: Pagination.DEFAULT_PAGE_NUMBER
    )
}

fun paginationWith(actions: PaginationBuilder.() -> Unit) : Pagination {
    val builder = PaginationBuilder()
    builder.actions()
    return builder.build()
}