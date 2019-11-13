package com.github.danzx.xof.dataprovider.jpa.spring.boot.autoconfigure

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan("com.github.danzx.xof.dataprovider.jpa.adapter")
@EntityScan("com.github.danzx.xof.dataprovider.jpa.entity")
@EnableJpaRepositories("com.github.danzx.xof.dataprovider.jpa.repository")
class XofPersistenceAdapterAutoConfiguration