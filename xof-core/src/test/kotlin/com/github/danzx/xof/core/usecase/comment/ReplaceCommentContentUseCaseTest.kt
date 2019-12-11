package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentUpdater
import com.github.danzx.xof.core.test.constants.TEST_COMMENT
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand

import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ReplaceCommentContentUseCaseTest {

    @MockK lateinit var getCommentByIdUseCase: GetCommentByIdUseCase
    @MockK lateinit var updater: CommentUpdater
    @InjectMockKs lateinit var useCase: ReplaceCommentContentUseCase

    @BeforeEach
    fun onBeforeTest() {
        every { getCommentByIdUseCase(TEST_COMMENT.id) } returns TEST_COMMENT.copy()
        every { updater.update(any()) } returnsArgument 0
    }

    @Test
    fun `should replace comment content when no exceptions happen`() {
        val command = ReplaceCommentContentCommand(id = TEST_COMMENT.id, value = "NewContent")
        val actual = useCase(command)

        actual should {
            it.id shouldBe TEST_COMMENT.id
            it.created shouldBe TEST_COMMENT.created
            it.user shouldBe TEST_COMMENT.user
            it.postId shouldBe TEST_COMMENT.postId
            it.parentId shouldBe TEST_COMMENT.parentId
            it.votes shouldBe TEST_COMMENT.votes
            it.content shouldBe command.value
            it.updated shouldNotBe TEST_COMMENT.updated
        }
    }
}