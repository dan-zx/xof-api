package com.github.danzx.xof.entrypoint.rest.request

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    var name: String? = null,

    @field:NotBlank
    var lastName: String? = null,

    @field:NotBlank
    var username: String? = null,

    @field:NotBlank
    @field:URL
    var avatarImageUrl: String? = null)
