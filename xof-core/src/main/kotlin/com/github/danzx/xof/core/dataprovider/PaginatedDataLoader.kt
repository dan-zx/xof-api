package com.github.danzx.xof.core.dataprovider

import com.github.danzx.xof.common.pagination.Page
import com.github.danzx.xof.common.pagination.Pagination
import com.github.danzx.xof.common.sort.SortSpec

interface PaginatedDataLoader<F, T> {
    fun loadPaginated(filter: F, pagination: Pagination = Pagination(), sorting: List<SortSpec> = emptyList()) : Page<T>
}
