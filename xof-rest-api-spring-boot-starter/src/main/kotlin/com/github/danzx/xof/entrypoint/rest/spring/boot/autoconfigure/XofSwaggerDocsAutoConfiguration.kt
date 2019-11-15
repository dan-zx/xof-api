package com.github.danzx.xof.entrypoint.rest.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(XofRestWsProperties::class)
@ConditionalOnProperty(
    prefix = "xof.rest.ws.swagger",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class XofSwaggerDocsAutoConfiguration(private val properties: XofRestWsProperties) {

    @Bean
    fun api() = Docket(SWAGGER_2)
        .apiInfo(swaggerMetaData())
        .host(properties.url)
        .select()
        .apis(basePackage("com.github.danzx.xof.entrypoint.rest.controller"))
        .build()!!

    private fun swaggerMetaData() = ApiInfoBuilder()
        .title(properties.swagger.title)
        .version(properties.swagger.version)
        .build()!!

}