package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.user.UserUsernameChecker

import javax.inject.Inject

@UseCaseComponent
class ValidateUsernameDoesNotExistUseCase : UseCase<String, Unit> {

    @Inject
    lateinit var checker: UserUsernameChecker

    override operator fun invoke(username: String) {
        if (checker.existsUsername(username)) throw UsernameAlreadyExistsException()
    }
}