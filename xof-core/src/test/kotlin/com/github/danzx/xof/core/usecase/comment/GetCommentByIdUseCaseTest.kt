package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentByIdLoader
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.test.constants.TEST_COMMENT

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetCommentByIdUseCaseTest {

    @MockK lateinit var loader : CommentByIdLoader
    @InjectMockKs lateinit var useCase: GetCommentByIdUseCase

    @Test
    fun `should get comment by id when loader finds a matching comment with the given id`() {
        val expected = TEST_COMMENT.copy()
        every { loader.loadById(expected.id) } returns expected
        val actual = useCase(expected.id)

        actual shouldBe expected
    }

    @Test
    fun `should throw CommentNotFoundException when loader can't find a matching comment with the given id`() {
        every { loader.loadById(any()) } returns null

        shouldThrow<CommentNotFoundException> { useCase(1) }
    }
}