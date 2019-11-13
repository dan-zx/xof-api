package com.github.danzx.xof.common.pagination

data class Page<T>(
    val data: Collection<T> = emptyList(),
    val metadata: Metadata = Metadata()) {

    val hasPrevious = metadata.number > 1
    val hasNext = metadata.number < metadata.totalPages
    val isLast = !hasNext

    data class Metadata(
        var total: Long = 0,
        var count: Int = 0,
        var totalPages: Int = 0,
        var number: Int = 1)
}