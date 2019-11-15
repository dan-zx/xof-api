package com.github.danzx.xof.entrypoint.rest.response.mapper

import com.github.danzx.xof.common.pagination.Page
import com.github.danzx.xof.entrypoint.rest.response.PageResponse

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

fun <T> Page<T>.toPageResponse() = PageResponse(
    data = data,
    links = buildLinks(),
    metadata = PageResponse.Metadata(
        total = metadata.total,
        count = metadata.count,
        totalPages = metadata.totalPages,
        number = metadata.number
    )
)

private fun <T> Page<T>.buildLinks(): PageResponse.Links {
    val selfUrl = buildLinkUrlForPage(metadata.number, metadata.count)
    val previousUrl = hasPrevious.then { buildLinkUrlForPage(metadata.number - 1, metadata.count) }
    val nextUrl = hasNext.then { buildLinkUrlForPage(metadata.number + 1, metadata.count) }
    return PageResponse.Links(previousUrl, selfUrl, nextUrl)
}

private fun buildLinkUrlForPage(pageNumber: Int, pageSize: Int) =
    ServletUriComponentsBuilder
        .fromCurrentRequest()
        .replaceQueryParam("page", pageNumber)
        .replaceQueryParam("size", pageSize)
        .build()
        .toUriString()


private fun <R> Boolean.then(mapper: () -> R) = if (this) mapper() else null
