package com.github.danzx.xof.app

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(XofProperties::class)
@ConditionalOnProperty(
    prefix = "xof.swagger",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class SwaggerConfiguration(private val properties: XofProperties) : WebMvcConfigurer {

    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
        .apiInfo(swaggerMetaData())
        .host(properties.url)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.github.danzx.xof.entrypoint.rest.controller"))
        .build()!!

    private fun swaggerMetaData() = ApiInfoBuilder()
        .title(properties.swagger.title)
        .version(properties.swagger.version)
        .build()!!

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html")
    }
}
