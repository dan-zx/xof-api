package com.github.danzx.xof.entrypoint.rest.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import java.nio.charset.StandardCharsets.UTF_8

@ExtendWith(SpringExtension::class)
@WebMvcTest(HealthRestController::class)
@TestPropertySource(properties=["spring.main.banner-mode=off"])
class HealthRestControllerTest {

    @Autowired private lateinit var mvc: MockMvc

    @Test
    fun `should check return 200 (OK) always`() {
        mvc.perform(
            get( "${BaseRestController.BASE_PATH}/health")
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(UTF_8.toString()))
            .andExpect(status().isOk)
            .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.value").value(true))
    }
}
