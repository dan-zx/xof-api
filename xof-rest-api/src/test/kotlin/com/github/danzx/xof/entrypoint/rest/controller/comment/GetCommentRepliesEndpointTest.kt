package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.response.PageResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.util.UriComponentsBuilder

import java.time.LocalDateTime.now

class GetCommentRepliesEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should get comment replies return 200 (OK) when no errors happen`() {
        val id = TEST_COMMENT.id
        val page = 1
        val pageSize = 10
        val selfLink = UriComponentsBuilder
            .fromHttpUrl(servletUri)
            .pathSegment("api", "comments", id.toString(), "replies")
            .queryParam(PaginationRequest.PAGE, page)
            // WebMvcTest is not consistent with real application when using ServletUriComponentsBuilder.fromCurrentRequest()
            //.queryParam(PaginationRequest.SIZE, pageSize)
            .toUriString()

        val expected = PageResponse(
            data = listOf(TEST_COMMENT.copy()),
            links = PageResponse.Links(
                self = selfLink
            ),
            metadata = PageResponse.Metadata(
                total = 1,
                count = 1,
                totalPages = 1,
                number = 1
            )
        )

        every { getCommentsUseCase(any()) } returns Page(
            data = expected.data,
            metadata = Page.Metadata(
                total = expected.metadata.total,
                count = expected.metadata.count,
                totalPages = expected.metadata.totalPages,
                number = expected.metadata.number
            )
        )

        val actual = mvc.perform(
            get("$basePath/$id/replies")
                .accept(APPLICATION_JSON)
                .param(PaginationRequest.PAGE, page.toString())
                .param(PaginationRequest.SIZE, pageSize.toString())
             )
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<PageResponse<Comment>>()

        actual shouldBe expected
    }

    @Test
    fun `should get comment replies return 400 (Bad Request) when comment id is invalid`() {
        val id = -1
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = "$basePath/$id/replies",
            timestamp = now()
        )

        val actual = mvc.perform(get("$basePath/$id/replies").accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    @Test
    fun `should get comment replies return 400 (Bad Request) when pagination parameters are invalid`() {
        val id = TEST_COMMENT.id
        val page = -1
        val pageSize = -1
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf(
                PaginationRequest.PAGE to "must be greater than or equal to 1",
                PaginationRequest.SIZE to "must be greater than or equal to 1"
            ),
            path = "$basePath/$id/replies",
            timestamp = now()
        )

        val actual = mvc.perform(
            get("$basePath/$id/replies")
                .accept(APPLICATION_JSON)
                .param(PaginationRequest.PAGE, page.toString())
                .param(PaginationRequest.SIZE, pageSize.toString())
             )
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }
}