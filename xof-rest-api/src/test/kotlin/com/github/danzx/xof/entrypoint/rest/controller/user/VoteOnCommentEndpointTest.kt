package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.EMPTY
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.mockk.every
import io.mockk.just
import io.mockk.runs

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.util.stream.Stream

class VoteOnCommentEndpointTest : UserRestControllerBaseTest() {

    @Test
    fun `should vote on comment return 204 (No Content) when userId is $ge 1, commentId is $ge 1, payload is valid, user exists and comment exists`() {
        every { voteOnCommentUseCase(any()) } just runs

        mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isNoContent)
            .andExpect(content().string(String.EMPTY))
    }

    @ParameterizedTest
    @CsvSource("0,0", "0,-1", "-1,0", "-1,-1")
    fun `should vote on comment return 400 (Bad Request) when userId is $le 0 or commentId is $le 0`(invalidUserId: Long, invalidCommentId: Long) {
        val requestPath = "$BASE_PATH/$invalidUserId/comments/$invalidCommentId/votes"
        val expected = VALIDATION_ERROR.copy(
            path = requestPath,
            fieldErrors = mapOf(
                "userId" to "must be greater than or equal to 1",
                "commentId" to "must be greater than or equal to 1"
            )
        )

        val actual = mvc.perform(
            put(requestPath)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should vote on comment return 400 (Bad Request) when userId is $ge 1, commentId is $ge 1 and payload is not valid`() {
        val invalidRequest = VoteRequest(null)
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("value" to "must not be null"),
            path = VALID_REQUEST_PATH
        )

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(invalidRequest.toJson()))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("notFoundExceptionsProvider")
    fun `should vote on comment return 404 (Not Found) when userId is $ge 1, commentId is $ge 1, payload is valid and either user does not exist or comment does not exist`(exceptionThrown: Exception, expectedMessage: String) {
        val expected = NOT_FOUND_ERROR.copy(
            message = expectedMessage,
            path = VALID_REQUEST_PATH
        )

        every { voteOnCommentUseCase(any()) } throws exceptionThrown

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    companion object ArgumentsProviders {

        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_COMMENT.user.id}/comments/${TEST_COMMENT.id}/votes"

        @JvmStatic
        private val VALID_REQUEST = VoteRequest(Vote.Direction.UP)

        @JvmStatic
        fun notFoundExceptionsProvider() =
            Stream.of(
                arguments(CommentNotFoundException(), "Comment not found"),
                arguments(UserNotFoundException(), "User not found"))
    }
}