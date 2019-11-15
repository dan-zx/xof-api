package com.github.danzx.xof.entrypoint.rest.handler

import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.entrypoint.rest.response.mapper.toResponseEntity

import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CoreExceptionsHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleException(ex: UserNotFoundException) = NOT_FOUND.toResponseEntity("User not found")

    @ExceptionHandler(PostNotFoundException::class)
    fun handleException(ex: PostNotFoundException) = NOT_FOUND.toResponseEntity("Post not found")

    @ExceptionHandler(CommentNotFoundException::class)
    fun handleException(ex: CommentNotFoundException) = NOT_FOUND.toResponseEntity("Comment not found")

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleException(ex: UsernameAlreadyExistsException) = CONFLICT.toResponseEntity("Username already exists")
}