package com.github.danzx.xof.core.spring.boot.autoconfigure

import com.github.danzx.xof.core.usecase.UseCase

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
    "com.github.danzx.xof.core.usecase",
    includeFilters = [ComponentScan.Filter(UseCase::class, type = FilterType.ASSIGNABLE_TYPE)])
class XofCoreAutoConfiguration