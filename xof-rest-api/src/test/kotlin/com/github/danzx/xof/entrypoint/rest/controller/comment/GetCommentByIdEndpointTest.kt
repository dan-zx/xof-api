package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.time.LocalDateTime.now

class GetCommentByIdEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should get comment by id returns 200 (OK) when use case finds the comment matching the given id`() {
        val id = TEST_COMMENT.id
        val expected = TEST_COMMENT.copy()
        every { getCommentByIdUseCase(id) } returns expected

        val actual = mvc.perform(get("$basePath/$id").accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<Comment>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @ValueSource(longs = [-1L, 0L])
    fun `should get comment by id returns 400 (Bad Request) when comment id is invalid`(invalidId: Long) {
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = "$basePath/$invalidId",
            timestamp = now()
        )

        val actual = mvc.perform(get("$basePath/$invalidId").accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    @Test
    fun `should get comment by id returns 404 (Not Found) when use case finds the comment matching the given id`() {
        val id = TEST_COMMENT.id
        every { getCommentByIdUseCase(any()) } throws CommentNotFoundException()
        val expected = ErrorResponse(
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            status = HttpStatus.NOT_FOUND.value(),
            message = "Comment not found",
            path = "$basePath/$id",
            timestamp = now()
        )

        val actual = mvc.perform(get("$basePath/$id").accept(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }
}