package com.github.danzx.xof.entrypoint.rest.spring.boot.autoconfigure

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@Configuration
@ComponentScan("com.github.danzx.xof.entrypoint.rest.controller", "com.github.danzx.xof.entrypoint.rest.handler")
class XofRestWsAdapterAutoConfiguration