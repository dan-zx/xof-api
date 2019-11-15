package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserPersister
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.mapper.toNewUser

import javax.inject.Inject

class CreateNewUserUseCase : UseCase<CreateNewUserCommand, User> {

    @Inject lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @Inject lateinit var persister: UserPersister

    override fun invoke(command: CreateNewUserCommand): User {
        validateUsernameDoesNotExistUseCase(command.username)
        return persister.save(command.toNewUser())
    }
}