package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserIdChecker
import com.github.danzx.xof.core.exception.UserNotFoundException
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
class ValidateUserIdExistsUseCaseTest {

    @MockK lateinit var checker: UserIdChecker
    @InjectMockKs lateinit var useCase: ValidateUserIdExistsUseCase

    @Test
    fun `should throw UserNotFoundException when user id doesn't exist`() {
        every { checker.existsId(TEST_USER.id) } returns false
        shouldThrow<UserNotFoundException> { useCase(TEST_USER.id) }
    }

    @Test
    fun `should not throw any exception when user id exists`() {
        every { checker.existsId(any()) } returns true
        try {
            useCase(TEST_USER.id)
        } catch (ex: Exception) {
            fail("No exceptions allowed here")
        }
    }
}