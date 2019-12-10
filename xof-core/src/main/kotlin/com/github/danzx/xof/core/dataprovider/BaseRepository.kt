package com.github.danzx.xof.core.dataprovider

import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

interface DataByIdLoader<ID, T> {
    fun loadById(id: ID) : T?
}

interface DataByIdRemover<ID> {
    fun removeById(id: ID)
}

interface DataIdChecker<ID> {
    fun existsId(id: ID) : Boolean
}

interface DataPersister<T> {
    fun save(t : T): T
}

interface DataUpdater<T> {
    fun update(t: T): T
}

interface PaginatedDataLoader<F, T> {
    fun loadPaginated(filter: F, pagination: Pagination = Pagination(), sorting: List<SortSpec> = emptyList()) : Page<T>
}
