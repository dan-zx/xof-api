package com.github.danzx.xof.app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class XofApplication

fun main(args: Array<String>) {
    SpringApplication.run(XofApplication::class.java, *args)
}
