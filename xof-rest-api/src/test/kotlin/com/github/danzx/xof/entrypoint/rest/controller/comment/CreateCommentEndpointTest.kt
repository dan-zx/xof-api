package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.time.LocalDateTime

class CreateCommentEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should create comment return 201 (Created) and a new comment when request is valid`() {
        every { createNewCommentUseCase(any()) } returns TEST_COMMENT.copy()

        val request = CreateCommentRequest(
            content = TEST_COMMENT.content,
            userId = TEST_COMMENT.user.id,
            postId = TEST_COMMENT.postId,
            parentId = TEST_COMMENT.parentId
        )

        val actual = mvc.perform(
            post(basePath)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(request.toJson())
                .characterEncoding(UTF_8.toString()))
            .andExpect(status().isCreated)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<Comment>()

        actual shouldBe TEST_COMMENT
    }

    @Test
    fun `should create comment return 400 (Bad Request) when request is not valid`() {
        val request = CreateCommentRequest(
            content = "",
            userId = null,
            postId = null,
            parentId = -1
        )

        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf(
                "content" to "must not be blank",
                "userId" to "must not be null",
                "postId" to "must not be null",
                "parentId" to "must be greater than or equal to 1"
            ),
            path = basePath,
            timestamp = LocalDateTime.now()
        )

        val actual = mvc.perform(
            post(basePath)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(request.toJson())
                .characterEncoding(UTF_8.toString()))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }
}