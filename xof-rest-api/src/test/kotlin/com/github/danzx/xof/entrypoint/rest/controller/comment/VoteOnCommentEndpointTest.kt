package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT
import com.github.danzx.xof.entrypoint.rest.test.TEST_USER

import io.mockk.every
import io.mockk.just
import io.mockk.runs

import org.hamcrest.Matchers.isEmptyString

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets
import java.time.LocalDateTime.now
import java.util.stream.Stream

class VoteOnCommentEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should replace comment content return 200 (Ok) when payload is valid, id is $ge 1 and comment exists`() {
        val id = TEST_COMMENT.id
        every { voteOnCommentUseCase(any()) } just runs

        mvc.perform(
                put("$basePath/$id/vote")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8.toString())
                    .content(VALID_REQUEST.toJson()))
            .andExpect(status().isNoContent)
            .andExpect(content().string(isEmptyString()))
    }

    @ParameterizedTest
    @MethodSource("invalidVoteRequestsProvider")
    fun `should replace comment content return 400 (Bad Request) when payload is not valid`(invalidRequest: VoteRequest, expectedFieldErrors: Map<String, String>) {
        val id = TEST_COMMENT.id
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = expectedFieldErrors,
            path = "$basePath/$id/vote",
            timestamp = now()
        )

        val actual = mvc.perform(
            put("$basePath/$id/vote")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(invalidRequest.toJson()))
            .andExpect(status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.header().string(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE
                ))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    @ParameterizedTest
    @ValueSource(longs = [-1L, 0L])
    fun `should replace comment content return 400 (Bad Request) when payload is valid and id is $le 0`(invalidId: Long) {
        val expected = ErrorResponse(
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            status = HttpStatus.BAD_REQUEST.value(),
            message = "Validation failed",
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = "$basePath/$invalidId/vote",
            timestamp = now()
        )

        val actual = mvc.perform(
            put("$basePath/$invalidId/vote")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isBadRequest)
            .andExpect(
                MockMvcResultMatchers.header().string(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE
                ))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    @ParameterizedTest
    @MethodSource("notFoundExceptionsProvider")
    fun `should vote on comment return 404 (Not Found) when payload is valid, id is $ge 1 and either comment does not exist or user does not exist`(exceptionThrown: Exception, expectedMessage: String) {
        val id = TEST_COMMENT.id
        val expected = ErrorResponse(
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            status = HttpStatus.NOT_FOUND.value(),
            message = expectedMessage,
            path = "$basePath/$id/vote",
            timestamp = now()
        )

        every { voteOnCommentUseCase(any()) } throws exceptionThrown

        val actual = mvc.perform(
            put("$basePath/$id/vote")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isNotFound)
            .andExpect(
                MockMvcResultMatchers.header().string(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_JSON_UTF8_VALUE
                ))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        verifyErrorResponse(actual, expected)
    }

    companion object ArgumentsProviders {

        @JvmStatic
        private val VALID_REQUEST = VoteRequest(direction=Vote.Direction.UP, userId=TEST_USER.id)

        @JvmStatic
        fun invalidVoteRequestsProvider() =
            Stream.of(
                arguments(
                    VoteRequest(direction=null, userId=null),
                    mapOf("direction" to "must not be null", "userId" to "must not be null")),
                arguments(
                    VoteRequest(direction=null, userId=0),
                    mapOf("direction" to "must not be null", "userId" to "must be greater than or equal to 1")),
                arguments(
                    VoteRequest(direction=null, userId=-1),
                    mapOf("direction" to "must not be null", "userId" to "must be greater than or equal to 1"))
            )

        @JvmStatic
        fun notFoundExceptionsProvider() =
            Stream.of(
                arguments(CommentNotFoundException(), "Comment not found"),
                arguments(UserNotFoundException(), "User not found"))
    }
}