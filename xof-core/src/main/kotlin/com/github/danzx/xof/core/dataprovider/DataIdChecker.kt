package com.github.danzx.xof.core.dataprovider

interface DataIdChecker<ID> {
    fun existsId(id: ID) : Boolean
}