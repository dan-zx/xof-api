package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.NotBlank

data class NonBlankStringRequest(
    @field:NotBlank
    var value: String? = null)
