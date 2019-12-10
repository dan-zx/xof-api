package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostPersister
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

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
class CreateNewPostUseCaseTest {

    @MockK lateinit var persister: PostPersister
    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @InjectMockKs lateinit var useCase: CreateNewPostUseCase

    @Test
    fun `should persist post when no exceptions happen`() {
        val createdDate = of(2019, 12, 6, 12, 0, 0)
        val command = CreateNewPostCommand(
            title = "Title",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1
        )
        val expected = Post(
            id = 1,
            title = command.title,
            content = command.content,
            created = createdDate,
            updated = createdDate,
            votes = 0,
            user = SimpleUser(
                id = command.userId,
                username = "username"
            )
        )
        every { persister.save(any()) } answers {
            firstArg<Post>().apply {
                id = expected.id
                created = createdDate
                updated = createdDate
                user.username = expected.user.username
            }
        }

        val actual = useCase(command)

        verify { validateUserIdExistsUseCase(command.userId) }
        actual shouldBe expected
    }
}