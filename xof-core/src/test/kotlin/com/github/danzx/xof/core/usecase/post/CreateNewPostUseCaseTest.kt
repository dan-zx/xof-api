package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostPersister
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.test.constants.TEST_POST
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

@ExtendWith(MockKExtension::class)
class CreateNewPostUseCaseTest {

    @MockK lateinit var persister: PostPersister
    @RelaxedMockK lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @InjectMockKs lateinit var useCase: CreateNewPostUseCase

    @Test
    fun `should persist post when no exceptions happen`() {
        val command = CreateNewPostCommand(
            title = "Title",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
            userId = 1
        )
        val expected = TEST_POST.copy(
            title = command.title,
            content = command.content,
            user = SimpleUser(
                id = command.userId,
                username = TEST_POST.user.username
            )
        )
        every { persister.save(any()) } returns expected

        val actual = useCase(command)

        verify { validateUserIdExistsUseCase(command.userId) }
        actual shouldBe expected
    }
}