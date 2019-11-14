package com.github.danzx.xof.common.pagination

data class Pagination(
    val size: Int = DEFAULT_PAGE_SIZE,
    val number: Int = DEFAULT_PAGE_NUMBER) {

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val DEFAULT_PAGE_NUMBER = 1
    }
}
