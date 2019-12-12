package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.Min

data class PaginationRequest(
    @field:Min(1)
    var page: Int? = null,

    @field:Min(1)
    var size: Int? = null) {

    companion object ParameterNames {
        const val PAGE = "page"
        const val SIZE = "size"
    }
}