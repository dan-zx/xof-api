package com.github.danzx.xof.entrypoint.rest.spring.boot.autoconfigure

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("xof.rest.api")
class XofRestWsProperties {
    lateinit var url: String
    var swagger: Swagger = Swagger()

    class Swagger {
        var enabled: Boolean? = false
        lateinit var title: String
        lateinit var version: String
    }
}
