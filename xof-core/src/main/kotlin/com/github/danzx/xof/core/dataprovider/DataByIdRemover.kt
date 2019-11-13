package com.github.danzx.xof.core.dataprovider

interface DataByIdRemover<ID> {
    fun removeById(id: ID)
}
