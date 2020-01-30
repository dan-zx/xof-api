package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.exception.UserNotFoundException
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
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

class GetUserByIdEndpointTest : UserRestControllerBaseTest() {

    @Test
    fun `should get user by id return 200 (Ok) when id is valid and user exists`() {
        val expected = TEST_USER.copy()

        every { getUserByIdUseCase(any()) } returns expected

        val actual = mvc.perform(get(VALID_REQUEST_PATH).accept(APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<User>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @ValueSource(longs = [-1L, 0L])
    fun `should get user by id return 400 (Bad Request) when id is not valid`(invalidId: Long) {
        val requestPath = "$BASE_PATH/$invalidId"
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = requestPath
        )

        val actual = mvc.perform(get(requestPath).accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should get user by id return 404 (Not Found) when id valid and user does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "User not found",
            path = VALID_REQUEST_PATH
        )

        every { getUserByIdUseCase(any()) } throws UserNotFoundException()

        val actual = mvc.perform(get(VALID_REQUEST_PATH).accept(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    companion object {
        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_USER.id}"
    }
}