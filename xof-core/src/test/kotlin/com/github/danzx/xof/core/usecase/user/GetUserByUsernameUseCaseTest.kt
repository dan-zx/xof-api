package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByUsernameLoader
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.test.MockKStringSpec

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK

import java.time.LocalDateTime.of

class GetUserByUsernameUseCaseTest : MockKStringSpec() {

    @MockK lateinit var loader: UserByUsernameLoader
    @InjectMockKs lateinit var useCase: GetUserByUsernameUseCase

    init {

        "should get user by username when loader finds a matching user with the given username" {
            val user = User(
                id = 1,
                name = "User",
                lastName = "UserLastName",
                username = "UserUsername",
                avatarImageUrl = "http://userimage.jpg",
                join = of(2019, 12, 6, 12, 0, 0)
            )
            every { loader.loadByUsername(user.username) } returns user
            val actual = useCase(user.username)

            actual shouldBe user
        }

        "should throw UserNotFoundException when loader can't find a matching user with the given username" {
            every { loader.loadByUsername(any()) } throws UserNotFoundException()

            shouldThrow<UserNotFoundException> { useCase("username") }
        }
    }
}