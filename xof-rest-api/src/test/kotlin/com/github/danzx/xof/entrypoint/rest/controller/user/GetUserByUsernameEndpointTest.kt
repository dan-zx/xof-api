package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.BAD_REQUEST_ERROR
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_USER
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class GetUserByUsernameEndpointTest : UserRestControllerBaseTest() {

    @Test
    fun `should get user by username return 200 (Ok) when username is valid and user exists`() {
        val expected = TEST_USER.copy()
        val username = expected.username

        every { getUserByUsernameUseCase(any()) } returns expected

        val actual = mvc.perform(
            get(BASE_PATH)
                .param("username", username)
                .accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<User>()

        actual shouldBe expected
    }

    @Test
    fun `should get user by username return 400 (Bad Request) when username is not present`() {
        val expected = BAD_REQUEST_ERROR.copy(
            message = "Query parameter 'username' is required",
            path = BASE_PATH
        )

        val actual = mvc.perform(get(BASE_PATH).accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " ", "\n", "\t"])
    fun `should get user by username return 400 (Bad Request) when username is blank`(invalidUsername: String) {
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("username" to "must not be blank"),
            path = BASE_PATH
        )

        val actual = mvc.perform(
            get(BASE_PATH)
                .param("username", invalidUsername)
                .accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should get user by username return 404 (Not Found) when username valid and user does not exist`() {
        val username = TEST_USER.username
        val expected = NOT_FOUND_ERROR.copy(
            message = "User not found",
            path = BASE_PATH
        )

        every { getUserByUsernameUseCase(any()) } throws UserNotFoundException()

        val actual = mvc.perform(
            get(BASE_PATH)
                .param("username", username)
                .accept(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }
}