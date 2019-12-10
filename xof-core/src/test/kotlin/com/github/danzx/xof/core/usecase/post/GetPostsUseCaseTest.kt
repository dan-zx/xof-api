package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.common.pagination.Page
import com.github.danzx.xof.common.pagination.dsl.page
import com.github.danzx.xof.common.pagination.dsl.paginationWith
import com.github.danzx.xof.common.sort.dsl.sortBy
import com.github.danzx.xof.core.dataprovider.PaginatedPostsLoader
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.filter.dsl.postsWith
import com.github.danzx.xof.core.filter.dsl.title
import com.github.danzx.xof.core.filter.dsl.userId
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand

import io.kotlintest.shouldBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import java.time.LocalDateTime.of

@ExtendWith(MockKExtension::class)
class GetPostsUseCaseTest {

    @MockK lateinit var loader : PaginatedPostsLoader
    @InjectMockKs lateinit var useCase: GetPostsUseCase

    @Test
    fun `should get posts when no exceptions happen`() {
        val expected = Page(
            data = listOf(Post(
                id = 1,
                title = "Title",
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                created = of(2019, 12, 6, 12, 0, 0),
                updated = of(2019, 12, 6, 12, 0, 0),
                votes = 0,
                user = SimpleUser(
                    id = 1,
                    username = "username"
                )
            )),
            metadata = Page.Metadata(
                total = 1,
                count = 1,
                totalPages = 1,
                number = 1
            )
        )

        every { loader.loadPaginated(any(), any(), any()) } returns expected

        val command = PostsLoaderCommand(
            postsWith {
                userId eq 1
                title containing "tle"
            },
            paginationWith {
                page size 10
                page number 1
            },
            sortBy { +"created" }
        )

        val actual = useCase(command)

        actual shouldBe expected
    }
}