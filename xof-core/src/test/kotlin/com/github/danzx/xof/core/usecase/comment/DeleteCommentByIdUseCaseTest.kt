package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentByIdRemover
import com.github.danzx.xof.core.test.constants.TEST_COMMENT

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DeleteCommentByIdUseCaseTest {

    @RelaxedMockK lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase
    @RelaxedMockK lateinit var remover: CommentByIdRemover
    @InjectMockKs lateinit var useCase: DeleteCommentByIdUseCase

    @Test
    fun `should remove comment by id when no exceptions happen`() {
        val id = TEST_COMMENT.id
        useCase(id)

        verifyOrder {
            validateCommentIdExistsUseCase(id)
            remover.removeById(id)
        }
    }
}