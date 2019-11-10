package com.github.danzx.xof.core.repository

interface DataPersister<T> {
    fun save(t : T): T
}
