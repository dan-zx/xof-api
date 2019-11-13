package com.github.danzx.xof.entrypoint.rest.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@ConditionalOnProperty(
    prefix = "xof.rest.ws.swagger",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class XofSwaggerUiAutoConfiguration : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
    }
}