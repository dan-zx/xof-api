package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserUsernameChecker
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.core.test.constants.TEST_USER

import io.kotlintest.fail
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ValidateUsernameDoesNotExistUseCaseTest {

    @MockK lateinit var checker: UserUsernameChecker
    @InjectMockKs lateinit var useCase: ValidateUsernameDoesNotExistUseCase

    @Test
    fun `should throw UsernameAlreadyExistsException when username already exists`() {
        every { checker.existsUsername(TEST_USER.username) } returns true
        shouldThrow<UsernameAlreadyExistsException> { useCase(TEST_USER.username) }
    }

    @Test
    fun `should not throw any exception when username already doesn't exist`() {
        every { checker.existsUsername(any()) } returns false
        try {
            useCase(TEST_USER.username)
        } catch (ex: Exception) {
            fail("No exceptions allowed here")
        }

    }
}