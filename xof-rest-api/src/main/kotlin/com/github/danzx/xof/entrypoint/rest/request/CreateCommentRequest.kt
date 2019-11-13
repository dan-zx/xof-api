package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateCommentRequest(
    @field:NotBlank
    var content: String,

    @field:NotNull
    @field:Min(1)
    var userId: Long,

    @field:NotNull
    @field:Min(1)
    var postId: Long,

    @field:Min(1)
    var parentId: Long? = null)