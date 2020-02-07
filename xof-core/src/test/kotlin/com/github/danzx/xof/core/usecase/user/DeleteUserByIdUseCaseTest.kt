package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByIdRemover
import com.github.danzx.xof.core.test.constants.TEST_USER

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteUserByIdUseCaseTest {

    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @RelaxedMockK lateinit var remover: UserByIdRemover
    @InjectMockKs lateinit var useCase: DeleteUserByIdUseCase

    @Test
    fun `should remove user by id when no exceptions happen`() {
        val id = TEST_USER.id
        useCase(id)

        verifyOrder {
            validateUserIdExistsUseCase(id)
            remover.removeById(id)
        }
    }
}
