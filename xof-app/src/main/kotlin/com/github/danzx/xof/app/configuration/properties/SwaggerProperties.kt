package com.github.danzx.xof.app.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("swagger")
data class SwaggerProperties(
    val enabled: Boolean = true,
    val title: String,
    val version: String,
    var host: String)