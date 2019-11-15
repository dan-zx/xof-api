package com.github.danzx.xof.app

import com.github.danzx.xof.core.usecase.UseCase

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors.basePackage
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

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
@ComponentScan("com.github.danzx.xof.entrypoint.rest.controller", "com.github.danzx.xof.entrypoint.rest.handler")
class XofRestWsAdapterConfiguration

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(XofRestWsProperties::class)
@ConditionalOnProperty(
    prefix = "xof.rest.ws.swagger",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class XofSwaggerDocsConfiguration(private val properties: XofRestWsProperties) {

    @Bean
    fun api() = Docket(DocumentationType.SWAGGER_2)
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

@Configuration
@ConditionalOnProperty(
    prefix = "xof.rest.ws.swagger",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = true
)
class XofSwaggerUiConfiguration : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/").setViewName("redirect:/swagger-ui.html")
    }
}

@ConfigurationProperties("xof.rest.api")
class XofRestWsProperties {
    lateinit var url: String
    var swagger: Swagger = Swagger()

    class Swagger {
        lateinit var title: String
        lateinit var version: String
    }
}
