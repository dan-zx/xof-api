package com.github.danzx.xof.entrypoint.rest.controller

import com.fasterxml.jackson.databind.ObjectMapper

import com.github.danzx.xof.entrypoint.rest.response.ErrorResponse

import io.kotlintest.matchers.date.beInToday
import io.kotlintest.should
import io.kotlintest.shouldBe

import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
abstract class SpringRestControllerTest {

    @Autowired protected lateinit var mvc: MockMvc
    @Autowired protected lateinit var jsonMapper: ObjectMapper

    protected fun verifyErrorResponse(actual: ErrorResponse, expected: ErrorResponse) {
        actual should {
            it.error shouldBe expected.error
            it.status shouldBe expected.status
            it.message shouldBe expected.message
            it.fieldErrors shouldBe expected.fieldErrors
            it.path shouldBe expected.path
            it.timestamp should beInToday()
        }
    }

    protected val servletUri: String
        get() {
            val request = mvc.perform(get("/")).andReturn().request
            return "${request.scheme}://${request.serverName}"
        }
}

