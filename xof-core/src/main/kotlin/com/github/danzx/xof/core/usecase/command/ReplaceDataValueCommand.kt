package com.github.danzx.xof.core.usecase.command

data class ReplaceDataValueCommand<T>(
    val id: Long,
    val value: T)
