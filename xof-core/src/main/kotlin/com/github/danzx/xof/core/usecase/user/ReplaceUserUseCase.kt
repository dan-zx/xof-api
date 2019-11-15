package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserUpdater
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.core.usecase.user.mapper.copyTo

import javax.inject.Inject

class ReplaceUserUseCase : UseCase<ReplaceUserCommand, User> {

    @Inject lateinit var getUserByIdUseCase: GetUserByIdUseCase
    @Inject lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @Inject lateinit var updater: UserUpdater

    override operator fun invoke(command: ReplaceUserCommand) : User {
        val user = getUserByIdUseCase(command.id)
        validateUsernameDoesNotExistsIfDifferent(user, command)
        command copyTo user
        updater.update(user)
        return user
    }

    private fun validateUsernameDoesNotExistsIfDifferent(old: User, replacement: ReplaceUserCommand) {
        if (replacement.username != old.username) {
            validateUsernameDoesNotExistUseCase(replacement.username)
        }
    }
}