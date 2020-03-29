package com.github.danzx.xof.app

import com.github.danzx.xof.app.configuration.Profiles

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import java.util.Locale

@SpringBootApplication
class XofApplication

fun main(args: Array<String>) {
    System.setProperty("spring.profiles.default", Profiles.DEFAULT)
    Locale.setDefault(Locale.ROOT)
    SpringApplication.run(XofApplication::class.java, *args)
}
