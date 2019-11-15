package com.github.danzx.xof.entrypoint.rest.mapper

import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse

import org.springframework.http.ResponseEntity

fun ErrorResponse.toResponseEntity() = ResponseEntity.status(status).body(this)