package com.github.danzx.xof.core.usecase.user.command

data class ReplaceUserCommand(
    var id: Long,
    var name: String,
    var lastName: String,
    var username: String,
    var avatarImageUrl: String
)