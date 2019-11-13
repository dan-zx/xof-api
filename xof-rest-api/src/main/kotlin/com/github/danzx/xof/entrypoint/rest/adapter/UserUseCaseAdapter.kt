package com.github.danzx.xof.entrypoint.rest.adapter

import com.github.danzx.xof.core.usecase.user.CreateNewUserUseCase
import com.github.danzx.xof.core.usecase.user.DeleteUserByIdUseCase
import com.github.danzx.xof.core.usecase.user.GetUserByIdUseCase
import com.github.danzx.xof.core.usecase.user.GetUserByUsernameUseCase
import com.github.danzx.xof.core.usecase.user.ReplaceUserUseCase
import com.github.danzx.xof.entrypoint.rest.mapper.toCreateNewUserCommand
import com.github.danzx.xof.entrypoint.rest.mapper.toReplaceUserCommand
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserUseCaseAdapter {

    @Autowired lateinit var createNewUserUseCase: CreateNewUserUseCase
    @Autowired lateinit var getUserByIdUseCase: GetUserByIdUseCase
    @Autowired lateinit var getUserByUsernameUseCase: GetUserByUsernameUseCase
    @Autowired lateinit var replaceUserUseCase: ReplaceUserUseCase
    @Autowired lateinit var deleteUserByIdUseCase: DeleteUserByIdUseCase

    fun create(request: CreateUserRequest) = createNewUserUseCase(request.toCreateNewUserCommand())

    fun getById(id: Long) = getUserByIdUseCase(id)

    fun getByUsername(username: String) = getUserByUsernameUseCase(username)

    fun replace(id: Long, request: ReplaceUserRequest) = replaceUserUseCase(request.toReplaceUserCommand(id))

    fun deleteById(id: Long) = deleteUserByIdUseCase(id)
}