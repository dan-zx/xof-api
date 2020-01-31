package com.github.danzx.xof.dataprovider.jpa.adapter

import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension


@DataJpaTest
@ExtendWith(SpringExtension::class)
@TestPropertySource(properties=[
    "spring.main.banner-mode=off",
    "spring.jpa.properties.hibernate.format_sql=true",
    "spring.jpa.properties.hibernate.show_sql=true"
])
abstract class PersistenceAdapterBaseTest
