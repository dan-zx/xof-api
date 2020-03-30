package com.github.danzx.xof.app.configuration.properties

import com.github.danzx.xof.app.configuration.Profiles.HEROKU

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

import java.net.URI

@Component
@Profile(HEROKU)
data class HerokuPostgresProperties(@Value("\${heroku.postgres.url}") private val herokuDatabaseUrl: String) {

    final val username: String
    final val password: String
    final val url: String

    init {
        val uri = URI(herokuDatabaseUrl)
        val userInfo = uri.userInfo.split(":")
        username = userInfo[0]
        password = userInfo[1]
        url = "jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=require"
    }
}