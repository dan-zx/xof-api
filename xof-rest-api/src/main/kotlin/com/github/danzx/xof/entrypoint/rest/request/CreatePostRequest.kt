package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreatePostRequest(
    @field:NotBlank
    var title: String? = null,

    @field:NotBlank
    var content: String? = null,

    @field:NotNull
    @field:Min(1)
    var userId: Long? = null)
