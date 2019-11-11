package com.github.danzx.xof.core.dataprovider

interface DataUpdater<T> {
    fun update(t: T): T
}
