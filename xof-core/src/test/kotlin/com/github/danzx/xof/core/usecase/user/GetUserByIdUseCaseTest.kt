package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByIdLoader
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.test.MockKStringSpec

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK

import java.time.LocalDateTime.of

class GetUserByIdUseCaseTest : MockKStringSpec() {

    @MockK lateinit var loader: UserByIdLoader
    @InjectMockKs lateinit var useCase: GetUserByIdUseCase

    init {

        "should get user by id when loader finds a matching user with the given id" {
            val user = User(
                id = 1,
                name = "User",
                lastName = "UserLastName",
                username = "UserUsername",
                avatarImageUrl = "http://userimage.jpg",
                join = of(2019, 12, 6, 12, 0, 0)
            )
            every { loader.loadById(user.id) } returns user
            val actual = useCase(user.id)

            actual shouldBe user
        }

        "should throw UserNotFoundException when loader can't find a matching user with the given id" {
            every { loader.loadById(any()) } throws UserNotFoundException()

            shouldThrow<UserNotFoundException> { useCase(1) }
        }
    }
}