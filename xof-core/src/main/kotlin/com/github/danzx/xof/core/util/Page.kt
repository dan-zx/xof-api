package com.github.danzx.xof.core.util

data class Page<T>(
    var data: Collection<T> = emptyList(),
    var metadata: Metadata) {

    data class Metadata(
        var total: Long = 0,
        var count: Int = 0,
        var totalPages: Int = 0,
        var number: Int = 1)
}