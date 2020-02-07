package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_USER
import com.github.danzx.xof.entrypoint.rest.test.USERNAME_ALREADY_EXISTS_ERROR
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.util.stream.Stream

class ReplaceUserEndpointTest : UserRestControllerBaseTest() {

    @Test
    fun `should replace user return 200 (Ok) when payload is valid and user exists`() {
        val expected = TEST_USER.copy(
            name = VALID_REQUEST.name!!,
            lastName = VALID_REQUEST.lastName!!,
            username = VALID_REQUEST.username!!,
            avatarImageUrl = VALID_REQUEST.avatarImageUrl!!)

        every { replaceUserUseCase(any()) } returns expected

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST.toJson())
                .characterEncoding(UTF_8.toString()))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<User>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("invalidRequestsProvider")
    fun `should replace user return 400 (Bad Request) when payload is not valid`(invalidRequest: ReplaceUserRequest) {
        val expected = VALIDATION_ERROR.copy(
            path = VALID_REQUEST_PATH,
            fieldErrors = mapOf(
                "name" to "must not be blank",
                "lastName" to "must not be blank",
                "username" to "must not be blank",
                "avatarImageUrl" to "must not be blank"
            )
        )

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
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
    fun `should replace user return 400 (Bad Request) when payload has an invalid avatarImageUrl`() {
        val request = VALID_REQUEST.copy(avatarImageUrl = "not an URL")
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("avatarImageUrl" to "must be a valid URL"),
            path = VALID_REQUEST_PATH
        )

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(request.toJson())
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
    fun `should replace user return 400 (Bad Request) when payload is valid and userId is $le 0`(invalidId: Long) {
        val requestPath = "$BASE_PATH/$invalidId"
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = requestPath
        )

        val actual = mvc.perform(
            put(requestPath)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(VALID_REQUEST.toJson())
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
    fun `should replace user return 404 (Not Found) when payload is valid and user does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "User not found",
            path = VALID_REQUEST_PATH
        )

        every { replaceUserUseCase(any()) } throws UserNotFoundException()

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

    @Test
    fun `should replace user return 409 (Conflict) when payload is valid and username already exists`() {
        val expected = USERNAME_ALREADY_EXISTS_ERROR.copy(path = VALID_REQUEST_PATH)

        every { replaceUserUseCase(any()) } throws UsernameAlreadyExistsException()

        val actual = mvc.perform(
            put(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isConflict)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    companion object ArgumentsProviders {

        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_USER.id}"

        private val VALID_REQUEST = ReplaceUserRequest(
            name = TEST_USER.name,
            lastName = TEST_USER.lastName,
            username = TEST_USER.username,
            avatarImageUrl = TEST_USER.avatarImageUrl
        )

        @JvmStatic
        fun invalidRequestsProvider() =
            Stream.of(
                ReplaceUserRequest(
                    name = null,
                    lastName = null,
                    username = null,
                    avatarImageUrl = null),
                ReplaceUserRequest(
                    name = "",
                    lastName = "",
                    username = "",
                    avatarImageUrl = "")
            )

    }
}