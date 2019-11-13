package com.github.danzx.xof.entrypoint.rest.utils

import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

fun <T> responseEntityForCreatedResource(id: Any, payload: T): ResponseEntity<T> {
    val location = ServletUriComponentsBuilder
        .fromCurrentRequestUri()
        .path("/{id}")
        .buildAndExpand(id)
        .toUri()
    return ResponseEntity.created(location).body(payload)
}
