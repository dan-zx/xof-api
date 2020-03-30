package com.github.danzx.xof.app.configuration

import com.github.danzx.xof.app.configuration.Profiles.HEROKU
import com.github.danzx.xof.app.configuration.properties.HerokuPostgresProperties

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile(HEROKU)
class HerokuPostgresConfiguration(private val properties: HerokuPostgresProperties) {

    @Bean
    fun dataSource() =
        DataSourceBuilder.create()
            .username(properties.username)
            .password(properties.password)
            .url(properties.url)
            .driverClassName("org.postgresql.Driver")
            .build()

}
