package com.github.danzx.xof.entrypoint.rest.response

import org.springframework.http.ResponseEntity

object ResponseEntities {
    val NO_CONTENT = ResponseEntity.noContent().build<Void>()
}
