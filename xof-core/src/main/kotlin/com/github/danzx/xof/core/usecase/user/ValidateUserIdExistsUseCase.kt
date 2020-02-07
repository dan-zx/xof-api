package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserIdChecker
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

class ValidateUserIdExistsUseCase : UseCase<Long, Unit> {

    @Inject lateinit var checker: UserIdChecker

    override operator fun invoke(id: Long) {
        if (!checker.existsId(id)) throw UserNotFoundException()
    }
}