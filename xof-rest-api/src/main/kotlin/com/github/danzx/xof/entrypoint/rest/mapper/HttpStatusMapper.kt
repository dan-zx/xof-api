package com.github.danzx.xof.entrypoint.rest.mapper

import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse

import org.springframework.http.HttpStatus
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.time.LocalDateTime.now

fun HttpStatus.toErrorResponse(message: String = "No message available") = ErrorResponse(
        error = reasonPhrase,
        message = message,
        path = ServletUriComponentsBuilder.fromCurrentRequest().build().path!!,
        status = value(),
        timestamp = now()
    )