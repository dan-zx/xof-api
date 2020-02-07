package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.NotNull

data class NonNullValueRequest<T>(
    @field:NotNull
    var value: T? = null)
