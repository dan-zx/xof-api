package com.github.danzx.xof.core.repository

interface DataByIdRemover<ID> {
    fun removeById(id: ID)
}
