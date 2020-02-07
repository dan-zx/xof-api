package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostByIdLoader
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.test.constants.TEST_POST

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetPostByIdUseCaseTest {

    @MockK lateinit var loader : PostByIdLoader
    @InjectMockKs lateinit var useCase: GetPostByIdUseCase

    @Test
    fun `should get post by id when loader finds a matching post with the given id`() {
        val expected = TEST_POST.copy()
        every { loader.loadById(expected.id) } returns expected
        val actual = useCase(expected.id)

        actual shouldBe expected
    }

    @Test
    fun `should throw PostNotFoundException when loader can't find a matching post with the given id`() {
        every { loader.loadById(any()) } returns null

        shouldThrow<PostNotFoundException> { useCase(1) }
    }
}