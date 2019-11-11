package com.github.danzx.xof.core.dataprovider

interface DataPersister<T> {
    fun save(t : T): T
}
