package com.github.danzx.xof.app

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("xof")
class XofProperties {
    lateinit var url: String
    var swagger: Swagger = Swagger()

    class Swagger {
        var enabled: Boolean = true
        lateinit var title: String
        lateinit var version: String
    }
}
