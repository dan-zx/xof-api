package com.github.danzx.xof.app.configuration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan(
    "com.github.danzx.xof.entrypoint.rest.controller",
    "com.github.danzx.xof.entrypoint.rest.handler")
class XofRestWsAdapterConfiguration : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*")
            .allowedMethods(
                HttpMethod.POST.name,
                HttpMethod.GET.name,
                HttpMethod.OPTIONS.name,
                HttpMethod.DELETE.name,
                HttpMethod.PUT.name,
                HttpMethod.PATCH.name)
            .allowedHeaders(
                HttpHeaders.ORIGIN,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT,
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
                HttpHeaders.AUTHORIZATION,
                "X-Requested-With",
                "X-Requested-With")
            .maxAge(3600)
    }
}