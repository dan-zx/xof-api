package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreatePostRequest(
    @field:NotBlank
    var title: String,

    @field:NotBlank
    var content: String,

    @field:NotNull
    @field:Min(1)
    var userId: Long)
