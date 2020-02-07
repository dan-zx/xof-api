package com.github.danzx.xof.entrypoint.rest.handler

import com.github.danzx.xof.entrypoint.rest.response.mapper.toResponseEntity

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RequiredQueryParamErrorHandler {

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleException(ex: MissingServletRequestParameterException) = BAD_REQUEST.toResponseEntity("Query parameter '${ex.parameterName}' is required")
}