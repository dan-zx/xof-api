package com.github.danzx.xof.core.repository

interface DataUpdater<T> {
    fun update(t: T): T
}
