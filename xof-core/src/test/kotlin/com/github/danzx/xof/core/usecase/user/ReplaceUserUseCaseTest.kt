package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserUpdater
import com.github.danzx.xof.core.test.constants.TEST_USER
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand

import io.kotlintest.shouldBe

import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class ReplaceUserUseCaseTest {

    @MockK lateinit var getUserByIdUseCase: GetUserByIdUseCase
    @MockK lateinit var updater: UserUpdater
    @RelaxedMockK lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @InjectMockKs lateinit var useCase: ReplaceUserUseCase

    @BeforeEach
    fun onBeforeTest() {
        every { updater.update(any()) } returnsArgument 0
        every { getUserByIdUseCase(TEST_USER.id) } returns TEST_USER.copy()
    }

    @Test
    fun `should replace user when user was found and username is the same as the old one`() {
        val command = ReplaceUserCommand(
            id = TEST_USER.id,
            name = "User",
            lastName = "UserLastName",
            username = TEST_USER.username,
            avatarImageUrl = "http://userimage.jpg"
        )
        val expected = TEST_USER.copy(
            name = command.name,
            lastName = command.lastName,
            username = command.username,
            avatarImageUrl = command.avatarImageUrl
        )
        val actual = useCase(command)

        verify { validateUsernameDoesNotExistUseCase wasNot called }
        actual shouldBe expected
    }

    @Test
    fun `should replace user when user was found and username doesn't exist`() {
        val command = ReplaceUserCommand(
            id = TEST_USER.id,
            name = "User",
            lastName = "UserLastName",
            username = "New User Username",
            avatarImageUrl = "http://userimage.jpg"
        )
        val expected = TEST_USER.copy(
            name = command.name,
            lastName = command.lastName,
            username = command.username,
            avatarImageUrl = command.avatarImageUrl
        )
        val actual = useCase(command)

        verify { validateUsernameDoesNotExistUseCase(command.username) }
        actual shouldBe expected
    }
}
