package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserPersister
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.test.MockKStringSpec
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand

import io.kotlintest.shouldBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify

import java.time.LocalDateTime.of

class CreateNewUserUseCaseTest : MockKStringSpec() {

    @MockK lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @MockK lateinit var persister: UserPersister
    @InjectMockKs lateinit var useCase: CreateNewUserUseCase

    init {
        "should persist user when no exceptions happen" {
            val command = CreateNewUserCommand(
                name = "User",
                lastName = "UserLastName",
                username = "UserUsername",
                avatarImageUrl = "http://userimage.jpg"
            )
            val expected = User(
                id = 1,
                name = command.name,
                lastName = command.lastName,
                username = command.username,
                avatarImageUrl = command.avatarImageUrl,
                join = of(2019, 12, 6, 12, 0, 0)
            )
            every { persister.save(any()) } answers {
                firstArg<User>().apply {
                    id = expected.id
                    join = expected.join
                }
            }
            val actual = useCase(command)

            verify { validateUsernameDoesNotExistUseCase(command.username) }
            actual shouldBe expected
        }
    }
}