package com.github.danzx.xof.core.repository

import com.github.danzx.xof.common.Page
import com.github.danzx.xof.common.Pagination
import com.github.danzx.xof.common.SortSpec

interface PaginatedDataLoader<F, T> {
    fun loadPaginated(filter: F, pagination: Pagination = Pagination(), sorting: List<SortSpec> = emptyList()) : Page<T>
}
