package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserUsernameChecker
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.core.test.MockKStringSpec

import io.kotlintest.fail
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK

class ValidateUsernameDoesNotExistUseCaseTest : MockKStringSpec() {

    @MockK lateinit var checker: UserUsernameChecker
    @InjectMockKs lateinit var useCase: ValidateUsernameDoesNotExistUseCase

    init {
        "should throw UsernameAlreadyExistsException when username already exists" {
            every { checker.existsUsername(any()) } returns true
            shouldThrow<UsernameAlreadyExistsException> { useCase("user") }
        }

        "should not throw any exception when username already doesn't exist" {
            every { checker.existsUsername(any()) } returns false
            try {
                useCase("user")
            } catch (ex: Exception) {
                fail("No exceptions allowed here")
            }

        }
    }
}