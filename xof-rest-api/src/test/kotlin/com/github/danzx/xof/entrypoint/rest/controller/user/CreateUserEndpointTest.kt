package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UsernameAlreadyExistsException
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_USER
import com.github.danzx.xof.entrypoint.rest.test.USERNAME_ALREADY_EXISTS_ERROR
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8
import java.util.stream.Stream

class CreateUserEndpointTest : UserRestControllerBaseTest() {

    @Test
    fun `should create user return 201 (Created) when payload is valid`() {
        val expected = TEST_USER.copy(
            name = VALID_REQUEST.name!!,
            lastName = VALID_REQUEST.lastName!!,
            username = VALID_REQUEST.username!!,
            avatarImageUrl = VALID_REQUEST.avatarImageUrl!!)

        every { createNewUserUseCase(any()) } returns expected

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
            .parseAs<User>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @MethodSource("invalidRequestsProvider")
    fun `should create user return 400 (Bad Request) when payload is not valid`(invalidRequest: CreateUserRequest) {
        val expected = VALIDATION_ERROR.copy(
            path = BASE_PATH,
            fieldErrors = mapOf(
                "name" to "must not be blank",
                "lastName" to "must not be blank",
                "username" to "must not be blank",
                "avatarImageUrl" to "must not be blank"
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

    @Test
    fun `should create user return 400 (Bad Request) when payload has an invalid avatarImageUrl`() {
        val request = VALID_REQUEST.copy(avatarImageUrl = "not an URL")
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("avatarImageUrl" to "must be a valid URL"),
            path = BASE_PATH
        )

        val actual = mvc.perform(
            post(BASE_PATH)
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

    @Test
    fun `should create user return 409 (Conflict) when payload is valid and username already exists`() {
        val expected = USERNAME_ALREADY_EXISTS_ERROR.copy(path = BASE_PATH)

        every { createNewUserUseCase(any()) } throws UsernameAlreadyExistsException()

        val actual = mvc.perform(
            post(BASE_PATH)
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

        private val VALID_REQUEST = CreateUserRequest(
            name = TEST_USER.name,
            lastName = TEST_USER.lastName,
            username = TEST_USER.username,
            avatarImageUrl = TEST_USER.avatarImageUrl
        )

        @JvmStatic
        fun invalidRequestsProvider() =
            Stream.of(
                CreateUserRequest(
                    name = null,
                    lastName = null,
                    username = null,
                    avatarImageUrl = null),
                CreateUserRequest(
                    name = "",
                    lastName = "",
                    username = "",
                    avatarImageUrl = "")
            )

    }
}