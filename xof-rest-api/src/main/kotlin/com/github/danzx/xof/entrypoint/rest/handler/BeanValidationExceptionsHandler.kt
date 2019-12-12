package com.github.danzx.xof.entrypoint.rest.handler

import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.response.mapper.toErrorResponse
import com.github.danzx.xof.entrypoint.rest.response.mapper.toMap
import com.github.danzx.xof.entrypoint.rest.response.mapper.toResponseEntity

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import javax.validation.ConstraintViolationException

@ControllerAdvice
class BeanValidationExceptionsHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleException(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        val errorResponse = buildBeanValidationErrorResponse()
        errorResponse.fieldErrors = ex.constraintViolations.toMap()
        return errorResponse.toResponseEntity()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = buildBeanValidationErrorResponse()
        errorResponse.fieldErrors = ex.bindingResult.fieldErrors.toMap()
        return errorResponse.toResponseEntity()
    }

    @ExceptionHandler(BindException::class)
    fun handleException(ex: BindException): ResponseEntity<ErrorResponse> {
        val errorResponse = buildBeanValidationErrorResponse()
        errorResponse.fieldErrors = ex.fieldErrors.toMap()
        return errorResponse.toResponseEntity()
    }

    private fun buildBeanValidationErrorResponse() = BAD_REQUEST.toErrorResponse("Validation failed")
}
