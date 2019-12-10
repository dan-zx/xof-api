package com.github.danzx.xof.core.usecase.user

import com.github.danzx.xof.core.dataprovider.UserByIdLoader
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import java.time.LocalDateTime.of

@ExtendWith(MockKExtension::class)
class GetUserByIdUseCaseTest {

    @MockK lateinit var loader: UserByIdLoader
    @InjectMockKs lateinit var useCase: GetUserByIdUseCase

    @Test
    fun `should get user by id when loader finds a matching user with the given id`() {
        val expected = User(
            id = 1,
            name = "User",
            lastName = "UserLastName",
            username = "UserUsername",
            avatarImageUrl = "http://userimage.jpg",
            join = of(2019, 12, 6, 12, 0, 0)
        )
        every { loader.loadById(expected.id) } returns expected
        val actual = useCase(expected.id)

        actual shouldBe expected
    }

    @Test
    fun `should throw UserNotFoundException when loader can't find a matching user with the given id`() {
        every { loader.loadById(any()) } returns null

        shouldThrow<UserNotFoundException> { useCase(1) }
    }
}
