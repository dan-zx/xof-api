package com.github.danzx.xof.entrypoint.rest.controller.post

import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.EMPTY
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_POST
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.mockk.every
import io.mockk.just
import io.mockk.runs

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DeletePostEndpointTest : PostRestControllerBaseTest() {

    @Test
    fun `should delete post return 204 (No Content) when id is $ge 1 and post exists`() {
        every { deletePostByIdUseCase(any()) } just runs

        mvc.perform(delete(VALID_REQUEST_PATH).accept(APPLICATION_JSON))
            .andExpect(status().isNoContent)
            .andExpect(content().string(String.EMPTY))
    }


    @ParameterizedTest
    @ValueSource(longs = [-1L, 0L])
    fun `should delete post return 400 (Bad Request) when id is $le 0`(invalidId: Long) {
        val requestPath = "$BASE_PATH/$invalidId"
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = requestPath
        )

        val actual = mvc.perform(delete(requestPath).accept(APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should delete post return 404 (Not Found) when id is $ge 1 and post does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "Post not found",
            path = VALID_REQUEST_PATH
        )

        every { deletePostByIdUseCase(any()) } throws PostNotFoundException()

        val actual = mvc.perform(delete(VALID_REQUEST_PATH).accept(APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    companion object {
        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_POST.id}"
    }
}