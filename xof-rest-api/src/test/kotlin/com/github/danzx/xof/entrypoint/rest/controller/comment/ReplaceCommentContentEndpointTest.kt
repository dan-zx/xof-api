package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.test.NOT_FOUND_ERROR
import com.github.danzx.xof.entrypoint.rest.test.TEST_COMMENT
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

class ReplaceCommentContentEndpointTest : CommentRestControllerBaseTest() {

    private val validRequest = ContentUpdateRequest("New Content")

    @Test
    fun `should replace comment content return 200 (Ok) when payload is valid, id is $ge 1 and comment exists`() {
        val expected = TEST_COMMENT.copy(content = validRequest.value!!)
        every { replaceCommentContentUseCase(any()) } returns expected

        val actual = mvc.perform(
            patch(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(validRequest.toJson()))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<Comment>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @NullAndEmptySource
    fun `should replace comment content return 400 (Bad Request) when payload is not valid`(illegalContent: String?) {
        val request = ContentUpdateRequest(illegalContent)
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
    fun `should replace comment content return 400 (Bad Request) when payload is valid and id is $le 0`(invalidId: Long) {
        val requestPath = "$BASE_PATH/$invalidId/content"
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("id" to "must be greater than or equal to 1"),
            path = requestPath
        )

        val actual = mvc.perform(
            patch(requestPath)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(validRequest.toJson()))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should replace comment content return 404 (Not Found) when payload is valid, id is $ge 1 and comment does not exist`() {
        val expected = NOT_FOUND_ERROR.copy(
            message = "Comment not found",
            path = VALID_REQUEST_PATH
        )

        every { replaceCommentContentUseCase(any()) } throws CommentNotFoundException()

        val actual = mvc.perform(
            patch(VALID_REQUEST_PATH)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .characterEncoding(UTF_8.toString())
                .content(validRequest.toJson()))
            .andExpect(status().isNotFound)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    companion object {
        private val VALID_REQUEST_PATH = "$BASE_PATH/${TEST_COMMENT.id}/content"
    }
}