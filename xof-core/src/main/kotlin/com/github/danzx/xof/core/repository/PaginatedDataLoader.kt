package com.github.danzx.xof.core.repository

import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

interface PaginatedDataLoader<F, T> {
    fun loadPaginated(filter: F, pagination: Pagination = Pagination(), sorting: List<SortSpec> = emptyList()) : Page<T>
}
