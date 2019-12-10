package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserPersister
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand

import io.kotlintest.shouldBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import java.time.LocalDateTime.of

@ExtendWith(MockKExtension::class)
class CreateNewUserUseCaseTest {

    @MockK lateinit var persister: UserPersister
    @RelaxedMockK lateinit var validateUsernameDoesNotExistUseCase: ValidateUsernameDoesNotExistUseCase
    @InjectMockKs lateinit var useCase: CreateNewUserUseCase

    @Test
    fun `should persist user when no exceptions happen`() {
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
