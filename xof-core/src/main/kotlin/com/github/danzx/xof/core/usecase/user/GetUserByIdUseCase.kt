package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.repository.user.UserByIdLoader

import javax.inject.Inject

@UseCaseComponent
class GetUserByIdUseCase : UseCase<Long, User> {

    @Inject
    lateinit var loader: UserByIdLoader

    override operator fun invoke(id: Long) = loader.loadById(id) ?: throw UserNotFoundException()
}