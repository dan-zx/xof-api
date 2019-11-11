package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import org.springframework.data.domain.Page

fun <T> Page<T>.toDomainPage() = DomainPage<T>(
    data = content,
    metadata = DomainPageMetadata(
        total = totalElements,
        count = size,
        totalPages = totalPages,
        number = number
    )
)
