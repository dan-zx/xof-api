package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.user.UserByUsernameLoader

import javax.inject.Inject

class GetUserByUsernameUseCase : UseCase<String, User> {

    @Inject lateinit var loader: UserByUsernameLoader

    override operator fun invoke(username: String) = loader.loadByUsername(username) ?: throw UserNotFoundException()
}