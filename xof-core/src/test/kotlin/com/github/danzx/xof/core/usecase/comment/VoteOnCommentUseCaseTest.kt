package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentVotePersister
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.test.constants.TEST_COMMENT
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verifyOrder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class VoteOnCommentUseCaseTest {

    @RelaxedMockK lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase
    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @RelaxedMockK lateinit var commentVotePersister : CommentVotePersister
    @InjectMockKs lateinit var useCase: VoteOnCommentUseCase

    @Test
    fun `should save or update the comment votes when no exceptions happen`() {
        val command = Vote(
            entityId = TEST_COMMENT.id,
            userId = TEST_COMMENT.user.id,
            direction = Vote.Direction.UP
        )
        useCase(command)

        verifyOrder {
            validateCommentIdExistsUseCase(command.entityId)
            validateUserIdExistsUseCase(command.userId)
            commentVotePersister.saveOrUpdate(command)
        }
    }
}