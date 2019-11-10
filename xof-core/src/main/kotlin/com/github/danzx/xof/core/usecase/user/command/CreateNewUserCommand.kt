package com.github.danzx.xof.core.usecase.user.command

data class CreateNewUserCommand(
    var name: String,
    var lastName: String,
    var username: String,
    var avatarImageUrl: String
)