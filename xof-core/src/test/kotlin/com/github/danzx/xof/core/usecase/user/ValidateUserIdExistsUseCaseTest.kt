package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserIdChecker
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.test.MockKStringSpec

import io.kotlintest.fail
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK

class ValidateUserIdExistsUseCaseTest : MockKStringSpec() {

    @MockK lateinit var checker: UserIdChecker
    @InjectMockKs lateinit var useCase: ValidateUserIdExistsUseCase

    init {
        "should throw UserNotFoundException when id doesn't exist" {
            every { checker.existsId(any()) } returns false
            shouldThrow<UserNotFoundException> { useCase(1) }
        }

        "should not throw any exception when id exists" {
            every { checker.existsId(any()) } returns true
            try {
                useCase(1)
            } catch (ex: Exception) {
                fail("No exceptions allowed here")
            }
        }
    }
}