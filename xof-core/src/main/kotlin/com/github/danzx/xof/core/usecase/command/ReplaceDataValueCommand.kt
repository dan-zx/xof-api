package com.github.danzx.xof.core.usecase.command

data class ReplaceDataValueCommand<T>(
    var id: Long,
    var value: T
)
