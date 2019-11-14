package com.github.danzx.xof.common.pagination

data class Page<T> (
    val data: Collection<T> = emptyList(),
    val metadata: Metadata) {

    val hasPrevious = metadata.number > 1
    val hasNext = metadata.number < metadata.totalPages

    data class Metadata(
        val total: Long = 0,
        val count: Int = 0,
        val totalPages: Int = 0,
        val number: Int = 1)
}