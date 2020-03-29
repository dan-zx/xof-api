package com.github.danzx.xof.entrypoint.rest.controller

import com.github.danzx.xof.entrypoint.rest.controller.BaseRestController.Companion.BASE_PATH
import com.github.danzx.xof.entrypoint.rest.response.SuccessResponse

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$BASE_PATH/health")
@Api(tags=["Health API"], description="Health endpoints")
class HealthRestController {

    @GetMapping
    @ApiOperation("Health check")
    @ApiResponses(ApiResponse(code = 200, message = "OK - Services are up and running"))
    fun check() = SuccessResponse(true)
}
