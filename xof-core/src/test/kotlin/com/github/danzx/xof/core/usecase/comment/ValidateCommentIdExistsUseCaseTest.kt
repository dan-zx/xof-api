package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentIdChecker
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.test.constants.TEST_COMMENT

import io.kotlintest.fail
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ValidateCommentIdExistsUseCaseTest {

    @MockK lateinit var checker: CommentIdChecker
    @InjectMockKs lateinit var useCase: ValidateCommentIdExistsUseCase

    @Test
    fun `should throw CommentNotFoundException when comment id doesn't exist`() {
        every { checker.existsId(TEST_COMMENT.id) } returns false
        shouldThrow<CommentNotFoundException> { useCase(TEST_COMMENT.id) }
    }

    @Test
    fun `should not throw any exception when comment id exists`() {
        every { checker.existsId(any()) } returns true
        try {
            useCase(TEST_COMMENT.id)
        } catch (ex: Exception) {
            fail("No exceptions allowed here")
        }
    }
}