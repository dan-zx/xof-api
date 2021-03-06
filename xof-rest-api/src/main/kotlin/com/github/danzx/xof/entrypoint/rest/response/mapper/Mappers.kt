package com.github.danzx.xof.entrypoint.rest.response.mapper

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import java.time.LocalDateTime.now
import javax.validation.ConstraintViolation

fun HttpStatus.toResponseEntity(message: String) = toErrorResponse(message).toResponseEntity()

fun Set<ConstraintViolation<*>>.toMap() = asSequence().map { it.propertyPath.last().toString() to it.message }.toMap()

fun List<FieldError>.toMap() = asSequence().map { it.field to it.defaultMessage.orEmpty() }.toMap()

fun ErrorResponse.toResponseEntity() = ResponseEntity.status(status).body(this)

fun User.toCreatedResponseEntity() = responseEntityForCreatedResource(id, this)

fun Post.toCreatedResponseEntity() = responseEntityForCreatedResource(id, this)

fun Comment.toCreatedResponseEntity() = responseEntityForCreatedResource(id, this)

fun HttpStatus.toErrorResponse(message: String) = ErrorResponse(
    error = reasonPhrase,
    message = message,
    path = ServletUriComponentsBuilder.fromCurrentRequest().build().path!!,
    status = value(),
    timestamp = now()
)

private fun <T> responseEntityForCreatedResource(id: Any, payload: T) =
    ResponseEntity.created(
        ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri())
        .body(payload)

