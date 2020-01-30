package com.github.danzx.xof.entrypoint.rest.controller.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.Usernames
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_POST
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.util.stream.Stream

class CreatePostEndpointTest : PostRestControllerBaseTest() {

    @Test
    fun `should create post return 201 (Created) when payload is valid`() {
        val expected = TEST_POST.copy(
            title = VALID_REQUEST.title!!,
            content = VALID_REQUEST.content!!,
            user = SimpleUser(id = VALID_REQUEST.userId!!, username = Usernames.NOT_AVAILABLE))

        every { createNewPostUseCase(any()) } returns expected

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
            .parseAs<Post>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("invalidRequestsProvider")
    fun `should create post return 400 (Bad Request) when payload is not valid`(invalidRequest: CreatePostRequest) {
        val expected = VALIDATION_ERROR.copy(
            path = BASE_PATH,
            fieldErrors = mapOf(
                "title" to "must not be blank",
                "content" to "must not be blank",
                "userId" to "must not be null"
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
    @ValueSource(longs = [-1L, 0L])
    fun `should create post return 400 (Bad Request) when payload has userId invalid`(invalidUserId: Long) {
        val invalidRequest = VALID_REQUEST.copy(userId = invalidUserId)
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("userId" to "must be greater than or equal to 1"),
            path = BASE_PATH
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

    @Test
    fun `should create post return 404 (Not Found) when payload is valid and user does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "User not found",
            path = BASE_PATH
        )

        every { createNewPostUseCase(any()) } throws UserNotFoundException()

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

        private val VALID_REQUEST = CreatePostRequest(
            title = TEST_POST.title,
            content = TEST_POST.content,
            userId = TEST_POST.user.id
        )

        @JvmStatic
        fun invalidRequestsProvider() =
            Stream.of(
                CreatePostRequest(
                    title = null,
                    content = null,
                    userId = null),
                CreatePostRequest(
                    title = "",
                    content = "",
                    userId = null)
            )
    }
}