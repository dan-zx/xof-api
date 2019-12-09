package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByIdRemover
import com.github.danzx.xof.core.test.MockKStringSpec

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify

class DeleteUserByIdUseCaseTest : MockKStringSpec() {

    @MockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @MockK lateinit var remover: UserByIdRemover
    @InjectMockKs lateinit var useCase: DeleteUserByIdUseCase

    init {
        "should remove user by id when no exceptions happen" {
            val id = 1L
            useCase(id)

            verify { validateUserIdExistsUseCase(id) }
            verify { remover.removeById(id) }
        }
    }
}