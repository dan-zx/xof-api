package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.Usernames
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.util.stream.Stream

class CreateCommentEndpointTest : CommentRestControllerBaseTest() {

    @Test
    fun `should create comment return 201 (Created) and a new comment when payload is valid`() {
        val expected = TEST_COMMENT.copy(
            content = VALID_REQUEST.content!!,
            user = SimpleUser(id = VALID_REQUEST.userId!!, username = Usernames.NOT_AVAILABLE),
            postId = VALID_REQUEST.postId!!,
            parentId = VALID_REQUEST.parentId)

        every { createNewCommentUseCase(any()) } returns expected

        val actual = mvc.perform(
            post(BASE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST.toJson())
                .characterEncoding(UTF_8.toString()))
            .andExpect(status().isCreated)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<Comment>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("invalidRequestsProvider")
    fun `should create comment return 400 (Bad Request) when payload is not valid`(invalidRequest: CreateCommentRequest) {
        val expected = VALIDATION_ERROR.copy(
            path = BASE_PATH,
            fieldErrors = mapOf(
                "content" to "must not be blank",
                "userId" to "must not be null",
                "postId" to "must not be null",
                "parentId" to "must be greater than or equal to 1"
            )
        )

        val actual = mvc.perform(
            post(BASE_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(invalidRequest.toJson())
                .characterEncoding(UTF_8.toString()))
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
    fun `should create comment return 404 (Not Found) when payload is valid and either post does not exist or user does not exist`(exceptionThrown: Exception, expectedMessage: String) {
        val expected = NOT_FOUND_ERROR.copy(
            message = expectedMessage,
            path = BASE_PATH
        )

        every { createNewCommentUseCase(any()) } throws exceptionThrown

        val actual = mvc.perform(
            post(BASE_PATH)
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

        private val VALID_REQUEST = CreateCommentRequest(
            content = TEST_COMMENT.content,
            userId = TEST_COMMENT.user.id,
            postId = TEST_COMMENT.postId,
            parentId = TEST_COMMENT.parentId
        )

        @JvmStatic
        fun invalidRequestsProvider() =
            Stream.of(
                CreateCommentRequest(
                    content = null,
                    userId = null,
                    postId = null,
                    parentId = -1),
                CreateCommentRequest(
                    content = "",
                    userId = null,
                    postId = null,
                    parentId = 0)
            )

        @JvmStatic
        fun notFoundExceptionsProvider() =
            Stream.of(
                arguments(UserNotFoundException(), "User not found"),
                arguments(PostNotFoundException(), "Post not found")
            )

    }
}