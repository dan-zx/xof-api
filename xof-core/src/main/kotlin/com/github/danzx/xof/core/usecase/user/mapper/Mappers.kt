package com.github.danzx.xof.core.usecase.user.mapper

import com.github.danzx.xof.core.domain.AUTO_GENERATED_ID
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand

import java.time.LocalDateTime.now

fun CreateNewUserCommand.toNewUser() = User(
    id = AUTO_GENERATED_ID,
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl,
    join = now()
)

infix fun ReplaceUserCommand.copyTo(user: User) {
    val replacement = this
    user.apply {
        name = replacement.name
        lastName = replacement.lastName
        username = replacement.username
        avatarImageUrl = replacement.avatarImageUrl
    }
}