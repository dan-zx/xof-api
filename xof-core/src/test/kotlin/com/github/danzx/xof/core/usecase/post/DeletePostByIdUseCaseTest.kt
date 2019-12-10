package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostByIdRemover

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeletePostByIdUseCaseTest {

    @RelaxedMockK lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase
    @RelaxedMockK lateinit var remover: PostByIdRemover
    @InjectMockKs lateinit var useCase: DeletePostByIdUseCase

    @Test
    fun `should remove post by id when no exceptions happen`() {
        val id = 1L
        useCase(id)

        verify { validatePostIdExistsUseCase(id) }
        verify { remover.removeById(id) }
    }
}