package com.github.danzx.xof.entrypoint.rest.response.mapper

import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
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
    val selfUrl = buildLinkUrlForPage(metadata.number)
    val previousUrl = hasPrevious.then { buildLinkUrlForPage(metadata.number - 1) }
    val nextUrl = hasNext.then { buildLinkUrlForPage(metadata.number + 1) }
    return PageResponse.Links(previousUrl, selfUrl, nextUrl)
}

private fun buildLinkUrlForPage(pageNumber: Int) =
    ServletUriComponentsBuilder
        .fromCurrentRequest()
        .replaceQueryParam(PaginationRequest.PAGE, pageNumber)
        .build()
        .toUriString()


private fun <R> Boolean.then(mapper: () -> R) = if (this) mapper() else null
