package com.github.danzx.xof.entrypoint.rest.request

import javax.validation.constraints.NotBlank

data class NonBlankStringValue(
    @field:NotBlank
    var value: String? = null)
