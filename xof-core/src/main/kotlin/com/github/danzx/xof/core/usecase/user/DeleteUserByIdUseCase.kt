package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.user.UserByIdRemover

import javax.inject.Inject

class DeleteUserByIdUseCase : UseCase<Long, Unit> {

    @Inject lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @Inject lateinit var remover: UserByIdRemover

    override operator fun invoke(id: Long) {
        validateUserIdExistsUseCase(id)
        remover.removeById(id)
    }
}