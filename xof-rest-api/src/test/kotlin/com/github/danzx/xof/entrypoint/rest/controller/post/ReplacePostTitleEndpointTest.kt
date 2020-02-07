package com.github.danzx.xof.entrypoint.rest.controller.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.TitleUpdateRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_POST
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8

class ReplacePostTitleEndpointTest : PostRestControllerBaseTest() {

    @Test
    fun `should replace post title return 200 (Ok) when payload is valid, id is $ge 1 and post exists`() {
        val expected = TEST_POST.copy(title = VALID_REQUEST.value!!)
        every { replacePostTitleUseCase(any()) } returns expected

        val actual = mvc.perform(
            patch(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(VALID_REQUEST.toJson()))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<Post>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun `should replace post title return 400 (Bad Request) when payload is not valid`(illegalTitle: String?) {
        val request = TitleUpdateRequest(illegalTitle)
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("value" to "must not be blank"),
            path = VALID_REQUEST_PATH
        )

        val actual = mvc.perform(
            patch(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(request.toJson()))
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
    fun `should replace post title return 400 (Bad Request) when payload is valid and id is $le 0`(invalidId: Long) {
        val requestPath = "$BASE_PATH/$invalidId/title"
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = requestPath
        )

        val actual = mvc.perform(
            patch(requestPath)
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
    fun `should replace post title return 404 (Not Found) when payload is valid, id is $ge 1 and post does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "Post not found",
            path = VALID_REQUEST_PATH
        )

        every { replacePostTitleUseCase(any()) } throws PostNotFoundException()

        val actual = mvc.perform(
            patch(VALID_REQUEST_PATH)
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

    companion object {
        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_POST.id}/title"
        private val VALID_REQUEST = TitleUpdateRequest("New title")
    }
}