package com.github.danzx.xof.core.dataprovider

interface DataByIdLoader<ID, T> {
    fun loadById(id: ID) : T?
}
