package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostIdChecker
import com.github.danzx.xof.core.exception.PostNotFoundException

import io.kotlintest.fail
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ValidatePostIdExistsUseCaseTest {

    @MockK lateinit var checker: PostIdChecker
    @InjectMockKs lateinit var useCase: ValidatePostIdExistsUseCase

    @Test
    fun `should throw PostNotFoundException when post id doesn't exist`() {
        every { checker.existsId(any()) } returns false
        shouldThrow<PostNotFoundException> { useCase(1) }
    }

    @Test
    fun `should not throw any exception when post id exists`() {
        every { checker.existsId(any()) } returns true
        try {
            useCase(1)
        } catch (ex: Exception) {
            fail("No exceptions allowed here")
        }
    }
}