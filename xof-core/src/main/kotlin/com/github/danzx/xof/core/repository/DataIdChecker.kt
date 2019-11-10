package com.github.danzx.xof.core.repository

interface DataIdChecker<ID> {
    fun existsId(id: ID) : Boolean
}