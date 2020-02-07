package com.github.danzx.xof.entrypoint.rest.controller.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse
import com.github.danzx.xof.entrypoint.rest.response.PageResponse
import com.github.danzx.xof.entrypoint.rest.test.TEST_POST
import com.github.danzx.xof.entrypoint.rest.test.VALIDATION_ERROR

import io.kotlintest.shouldBe

import io.mockk.every

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.util.UriComponentsBuilder

class GetPostsEndpointTest : PostRestControllerBaseTest() {

    @Test
    fun `should get posts return 200 (Ok) when no errors happen`() {
        val page = 1
        val pageSize = 10
        val selfLink = UriComponentsBuilder
            .fromHttpUrl(servletUri)
            .path(BASE_PATH)
            .queryParam(PaginationRequest.PAGE, page)
            // WebMvcTest is not consistent with real application when using ServletUriComponentsBuilder.fromCurrentRequest()
            //.queryParam(PaginationRequest.SIZE, pageSize)
            .toUriString()

        val expected = PageResponse(
            data = listOf(TEST_POST.copy()),
            links = PageResponse.Links(
                self = selfLink
            ),
            metadata = PageResponse.Metadata(
                total = 1,
                count = 1,
                totalPages = 1,
                number = 1
            )
        )

        every { getPostsUseCase(any()) } returns Page(
            data = expected.data,
            metadata = Page.Metadata(
                total = expected.metadata.total,
                count = expected.metadata.count,
                totalPages = expected.metadata.totalPages,
                number = expected.metadata.number
            )
        )

        val actual = mvc.perform(
            get(BASE_PATH)
                .accept(APPLICATION_JSON)
                .param(PaginationRequest.PAGE, page.toString())
                .param(PaginationRequest.SIZE, pageSize.toString()))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<PageResponse<Post>>()

        actual shouldBe expected
    }

    @ParameterizedTest
    @CsvSource("0,0", "0,-1", "-1,0", "-1,-1")
    fun `should get posts return 400 (Bad Request) when pagination parameters are invalid`(invalidPage: Int, invalidPageSize: Int) {
        val expected = VALIDATION_ERROR.copy(
            path = BASE_PATH,
            fieldErrors = mapOf(
                PaginationRequest.PAGE to "must be greater than or equal to 1",
                PaginationRequest.SIZE to "must be greater than or equal to 1"
            )
        )

        val actual = mvc.perform(
            get(BASE_PATH)
                .accept(APPLICATION_JSON)
                .param(PaginationRequest.PAGE, invalidPage.toString())
                .param(PaginationRequest.SIZE, invalidPageSize.toString()))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }

    @Test
    fun `should get posts return 400 (Bad Request) when q parameter is invalid`() {
        val expected = VALIDATION_ERROR.copy(
            fieldErrors = mapOf("q" to "must not be blank"),
            path = BASE_PATH
        )

        val actual = mvc.perform(
            get(BASE_PATH)
                .accept(APPLICATION_JSON)
                .param("q", ""))
            .andExpect(status().isBadRequest)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andReturn()
            .response
            .contentAsString
            .parseAs<ErrorResponse>()

        actual shouldBe expected
    }
}