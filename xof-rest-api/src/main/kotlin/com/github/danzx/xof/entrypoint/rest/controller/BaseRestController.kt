package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.core.usecase.UseCaseExecutor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated

@Validated
abstract class BaseRestController {

    @Autowired lateinit var useCaseExecutor: UseCaseExecutor
}