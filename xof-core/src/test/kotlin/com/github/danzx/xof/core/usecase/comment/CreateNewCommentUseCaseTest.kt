package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentPersister
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.test.constants.TEST_COMMENT
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.post.ValidatePostIdExistsUseCase
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import io.kotlintest.shouldBe

import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyOrder

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateNewCommentUseCaseTest {

    @MockK lateinit var persister: CommentPersister
    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @RelaxedMockK lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase
    @RelaxedMockK lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase
    @InjectMockKs lateinit var useCase: CreateNewCommentUseCase

    @Test
    fun `should persist comment when no exceptions happen`() {
        val command = CreateNewCommentCommand(
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1,
            postId = 2,
            parentId = 3
        )
        val expected = command.createComment()
        every { persister.save(any()) } returns expected

        val actual = useCase(command)

        verifyOrder {
            validateUserIdExistsUseCase(command.userId)
            validatePostIdExistsUseCase(command.postId)
            validateCommentIdExistsUseCase(command.parentId!!)
        }

        actual shouldBe expected
    }

    @Test
    fun `should persist comment with no parent id when no exceptions happen`() {
        val command = CreateNewCommentCommand(
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1,
            postId = 2,
            parentId = null
        )
        val expected = command.createComment()
        every { persister.save(any()) } returns expected

        val actual = useCase(command)

        verify { validateCommentIdExistsUseCase wasNot called }
        verifyOrder {
            validateUserIdExistsUseCase(command.userId)
            validatePostIdExistsUseCase(command.postId)
        }

        actual shouldBe expected
    }

    private fun CreateNewCommentCommand.createComment() =
        TEST_COMMENT.copy(
            content = this.content,
            postId = this.postId,
            parentId = this.parentId,
            user = SimpleUser(
                id = this.userId,
                username = TEST_COMMENT.user.username
            )
        )
}