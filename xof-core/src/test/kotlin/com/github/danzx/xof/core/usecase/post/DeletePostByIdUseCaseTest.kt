package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostByIdRemover
import com.github.danzx.xof.core.test.constants.TEST_POST

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeletePostByIdUseCaseTest {

    @RelaxedMockK lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase
    @RelaxedMockK lateinit var remover: PostByIdRemover
    @InjectMockKs lateinit var useCase: DeletePostByIdUseCase

    @Test
    fun `should remove post by id when no exceptions happen`() {
        val id = TEST_POST.id
        useCase(id)

        verifyOrder {
            validatePostIdExistsUseCase(id)
            remover.removeById(id)
        }
    }
}