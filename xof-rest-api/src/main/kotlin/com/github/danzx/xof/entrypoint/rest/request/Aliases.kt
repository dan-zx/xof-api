package com.github.danzx.xof.entrypoint.rest.request

import com.github.danzx.xof.core.domain.Vote

typealias ReplaceUserRequest = CreateUserRequest
typealias ContentUpdateRequest = NonBlankStringRequest
typealias TitleUpdateRequest = NonBlankStringRequest
typealias VoteRequest = NonNullValueRequest<Vote.Direction>
