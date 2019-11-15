package com.github.danzx.xof.entrypoint.rest.handler

import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.entrypoint.rest.response.mapper.toErrorResponse
import com.github.danzx.xof.entrypoint.rest.response.mapper.toResponseEntity

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CoreExceptionsHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleException(ex: UserNotFoundException) = notFoundResponse("User not found")

    @ExceptionHandler(PostNotFoundException::class)
    fun handleException(ex: PostNotFoundException) = notFoundResponse("Post not found")

    @ExceptionHandler(CommentNotFoundException::class)
    fun handleException(ex: CommentNotFoundException) = notFoundResponse("Comment not found")

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleException(ex: UsernameAlreadyExistsException) = CONFLICT.toErrorResponse("Username already exists").toResponseEntity()

    private fun notFoundResponse(message: String) = NOT_FOUND.toErrorResponse(message).toResponseEntity()
}