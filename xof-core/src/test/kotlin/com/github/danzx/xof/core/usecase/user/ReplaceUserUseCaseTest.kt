package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserUpdater
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.test.MockKStringSpec
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand

import io.kotlintest.TestCase
import io.kotlintest.shouldBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify

import java.time.LocalDateTime.of

class ReplaceUserUseCaseTest : MockKStringSpec() {

    @MockK lateinit var getUserByIdUseCase: GetUserByIdUseCase
    @MockK lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @MockK lateinit var updater: UserUpdater
    @InjectMockKs lateinit var useCase: ReplaceUserUseCase

    private val testUser = User(
        id = 1,
        name = "User",
        lastName = "UserLastName",
        username = "UserUsername",
        avatarImageUrl = "http://userimage.jpg",
        join = of(2019, 12, 6, 12, 0, 0)
    )

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)
        every { updater.update(any()) } returnsArgument 0
        every { getUserByIdUseCase(any()) } returns testUser
    }

    init {
        "should replace user when user was found and username is the same as the old one" {
            val command = ReplaceUserCommand(
                id = 1,
                name = "User",
                lastName = "UserLastName",
                username = "UserUsername",
                avatarImageUrl = "http://userimage.jpg"
            )
            val expected = testUser.copy()
            val actual = useCase(command)

            verify(exactly = 0) { validateUsernameDoesNotExistUseCase(any()) }
            actual shouldBe expected
        }

        "should replace user when user was found and username doesn't exist" {
            val command = ReplaceUserCommand(
                id = 1,
                name = "User",
                lastName = "UserLastName",
                username = "otherUsername",
                avatarImageUrl = "http://userimage.jpg"
            )

            val expected = testUser.copy(username = command.username)
            val actual = useCase(command)

            verify { validateUsernameDoesNotExistUseCase(command.username) }
            actual shouldBe expected
        }
    }
}