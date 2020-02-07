package com.github.danzx.xof.entrypoint.rest.response

data class PageResponse<T>(
    var data: Collection<T> = emptyList(),
    var links: Links,
    var metadata: Metadata) {

    data class Links(
        var previous: String? = null,
        var self: String,
        var next: String? = null
    )

    data class Metadata(
        var total: Long = 0,
        var count: Int = 0,
        var totalPages: Int = 0,
        var number: Int = 1
    )
}
