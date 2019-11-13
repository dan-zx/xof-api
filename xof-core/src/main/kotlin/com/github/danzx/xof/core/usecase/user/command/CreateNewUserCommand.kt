package com.github.danzx.xof.core.usecase.user.command

data class CreateNewUserCommand(
    val name: String,
    val lastName: String,
    val username: String,
    val avatarImageUrl: String)