package com.github.danzx.xof.core.util.dsl

import com.github.danzx.xof.core.util.Pagination

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PaginationDslTest : StringSpec({

    "paginationWith should create Pagination object" {
        val expected = Pagination(size = 5, number = 53)
        val actual = paginationWith {
            page size 5
            page number 53
        }

        actual shouldBe expected
    }
})