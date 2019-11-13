package com.github.danzx.xof.common.pagination

object page

class PaginationBuilder {
    private var size: Int? = null
    private var number: Int? = null

    infix fun page.size(v: Int?) {
        size = v
    }

    infix fun page.number(v: Int?) {
        number = v
    }

    fun build() =
        Pagination(size ?: Pagination.DEFAULT_PAGE_SIZE, number ?: Pagination.DEFAULT_PAGE_NUMBER)
}

fun paginationWith(actions: PaginationBuilder.() -> Unit) : Pagination {
    val builder = PaginationBuilder()
    builder.actions()
    return builder.build()
}
