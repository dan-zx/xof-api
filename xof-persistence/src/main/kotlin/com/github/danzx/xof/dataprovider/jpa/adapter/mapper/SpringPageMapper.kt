package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import org.springframework.data.domain.Page

fun <T> Page<T>.toDomainPage() = DomainPage<T>(
    content,
    DomainPageMetadata(
        totalElements,
        size,
        totalPages,
        number
    )
)
