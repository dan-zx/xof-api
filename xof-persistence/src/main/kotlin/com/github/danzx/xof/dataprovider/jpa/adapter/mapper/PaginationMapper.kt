package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

infix fun Pagination.with(sorting: List<SortSpec>) : Pageable = PageRequest.of(this.number-1, this.size, sorting.toSort())

private fun List<SortSpec>.toSort() = Sort.by(this.map { it.toSortOrder() })

private fun SortSpec.toSortOrder() = Sort.Order(Sort.Direction.fromString(this.direction.name), this.property)