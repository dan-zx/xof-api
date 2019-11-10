package com.github.danzx.xof.core.repository

interface DataByIdLoader<ID, T> {
    fun loadById(id: ID) : T?
}
