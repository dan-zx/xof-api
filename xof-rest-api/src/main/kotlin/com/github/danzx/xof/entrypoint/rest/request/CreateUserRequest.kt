package com.github.danzx.xof.entrypoint.rest.request

import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank
    var name: String,

    @field:NotBlank
    var lastName: String,

    @field:NotBlank
    var username: String,

    @field:NotBlank
    @field:URL
    var avatarImageUrl: String)
