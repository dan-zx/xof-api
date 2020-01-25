package com.github.danzx.xof.dataprovider.jpa.repository.specification

import org.springframework.data.jpa.domain.Specification

interface JpaEntitySpecifications<T> {

    @Suppress("UNCHECKED_CAST")
    val empty: Specification<T>
        get() = EMPTY as Specification<T>

    fun predicates() = Specification.where(empty)

    companion object {
        val EMPTY: Specification<Any> = Specification { _, _, _ -> null }
    }
}