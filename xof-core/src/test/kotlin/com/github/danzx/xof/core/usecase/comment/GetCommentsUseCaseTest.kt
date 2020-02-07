package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.PaginatedCommentsLoader
import com.github.danzx.xof.core.filter.dsl.commentsWith
import com.github.danzx.xof.core.filter.dsl.parentId
import com.github.danzx.xof.core.filter.dsl.postId
import com.github.danzx.xof.core.filter.dsl.userId
import com.github.danzx.xof.core.test.constants.TEST_COMMENT
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.util.dsl.page
import com.github.danzx.xof.core.util.dsl.paginationWith
import com.github.danzx.xof.core.util.dsl.sortBy

import io.kotlintest.shouldBe

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetCommentsUseCaseTest {

    @MockK lateinit var loader : PaginatedCommentsLoader
    @InjectMockKs lateinit var useCase: GetCommentsUseCase

    @Test
    fun `should get comments when no exceptions happen`() {
        val expected = Page(
            data = listOf(TEST_COMMENT.copy()),
            metadata = Page.Metadata(
                total = 1,
                count = 1,
                totalPages = 1,
                number = 1
            )
        )
        every { loader.loadPaginated(any(), any(), any()) } returns expected

        val command = CommentsLoaderCommand(
            commentsWith {
                userId eq 1
                postId eq 2
                parentId eq 3
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