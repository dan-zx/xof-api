package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT

import io.mockk.every
import io.mockk.just
import io.mockk.runs

import org.hamcrest.Matchers.isEmptyString

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.time.LocalDateTime

class DeleteCommentEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should delete comment return 204 (No Content) when id is $ge 1 and comment exists`() {
        val id = TEST_COMMENT.id

        every { deleteCommentByIdUseCase(any()) } just runs

        mvc.perform(delete("$basePath/$id").accept(APPLICATION_JSON))
            .andExpect(status().isNoContent)
            .andExpect(content().string(isEmptyString()))
    }


    @ParameterizedTest
    @ValueSource(longs = [-1L, 0L])
    fun `should delete comment return 400 (Bad Request) when id is $le 0`(invalidId: Long) {
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = "$basePath/$invalidId",
            timestamp = LocalDateTime.now()
        )

        val actual = mvc.perform(delete("$basePath/$invalidId").accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    @Test
    fun `should delete comment return 404 (Not Found) when id is $ge 1 and comment does not exist`() {
        val id = TEST_COMMENT.id
        val expected = ErrorResponse(
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            status = HttpStatus.NOT_FOUND.value(),
            message = "Comment not found",
            path = "$basePath/$id",
            timestamp = LocalDateTime.now()
        )

        every { deleteCommentByIdUseCase(any()) } throws CommentNotFoundException()

        val actual = mvc.perform(delete("$basePath/$id").accept(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

}