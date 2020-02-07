package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByUsernameLoader
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.test.constants.TEST_USER

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetUserByUsernameUseCaseTest {

    @MockK lateinit var loader: UserByUsernameLoader
    @InjectMockKs lateinit var useCase: GetUserByUsernameUseCase

    @Test
    fun `should get user by username when loader finds a matching user with the given username`() {
        val expected = TEST_USER.copy()
        every { loader.loadByUsername(expected.username) } returns expected
        val actual = useCase(expected.username)

        actual shouldBe expected
    }

    @Test
    fun `should throw UserNotFoundException when loader can't find a matching user with the given username`() {
        every { loader.loadByUsername(any()) } returns null

        shouldThrow<UserNotFoundException> { useCase("username") }
    }
}
