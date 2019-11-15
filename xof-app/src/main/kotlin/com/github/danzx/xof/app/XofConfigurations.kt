package com.github.danzx.xof.app

import com.github.danzx.xof.core.usecase.UseCase

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

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
