package com.github.danzx.xof.entrypoint.rest.utils

import org.springframework.web.servlet.support.ServletUriComponentsBuilder

typealias SpringResponseEntity<T> = org.springframework.http.ResponseEntity<T>

class ResponseEntity {
    companion object {
        fun <T> created(id: Any, payload: T) =
            SpringResponseEntity.created(
                ServletUriComponentsBuilder
                    .fromCurrentRequestUri()
                    .path("/{id}")
                    .buildAndExpand(id)
                    .toUri())
                .body(payload)

        fun noContent() = SpringResponseEntity.noContent().build<Void>()
    }
}