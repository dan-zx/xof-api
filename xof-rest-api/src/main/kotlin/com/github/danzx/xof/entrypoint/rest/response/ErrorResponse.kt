package com.github.danzx.xof.entrypoint.rest.response

import java.time.LocalDateTime

data class ErrorResponse(
    var error: String,
    var message: String,
    var path: String,
    var status: Int,
    var timestamp: LocalDateTime)