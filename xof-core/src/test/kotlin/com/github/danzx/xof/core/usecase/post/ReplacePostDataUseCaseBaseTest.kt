package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.dataprovider.PostUpdater
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
abstract class ReplacePostDataUseCaseBaseTest<T: ReplacePostDataUseCase> {

    @MockK lateinit var getPostByIdUseCase: GetPostByIdUseCase
    @RelaxedMockK lateinit var updater: PostUpdater
    @InjectMockKs lateinit var useCase: T

    val testPost = Post(
        id = 1,
        title = "Title",
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        created = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
        updated = LocalDateTime.of(2019, 12, 6, 12, 0, 0),
        votes = 0,
        user = SimpleUser(
            id = 1,
            username = "username"
        )
    )

    @BeforeEach
    fun onBeforeTest() {
        every { getPostByIdUseCase(testPost.id) } returns testPost
        every { updater.update(any()) } returnsArgument 0
    }
}