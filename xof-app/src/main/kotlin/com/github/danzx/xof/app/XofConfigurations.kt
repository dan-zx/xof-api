package com.github.danzx.xof.app

import com.github.danzx.xof.core.usecase.UseCase

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ComponentScan(
    "com.github.danzx.xof.core.usecase",
    includeFilters = [ComponentScan.Filter(UseCase::class, type = FilterType.ASSIGNABLE_TYPE)])
class XofCoreConfiguration

@Configuration
@ComponentScan("com.github.danzx.xof.dataprovider.jpa.adapter")
@EntityScan("com.github.danzx.xof.dataprovider.jpa.entity")
@EnableJpaRepositories("com.github.danzx.xof.dataprovider.jpa.repository")
class XofPersistenceAdapterConfiguration

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
