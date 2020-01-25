package com.github.danzx.xof.entrypoint.rest.request

import com.github.danzx.xof.core.domain.Vote
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class VoteRequest(
    @field:NotNull
    var direction: Vote.Direction? = null,

    @field:NotNull
    @field:Min(1)
    var userId: Long? = null)