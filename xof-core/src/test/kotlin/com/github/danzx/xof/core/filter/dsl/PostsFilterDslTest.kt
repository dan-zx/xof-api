package com.github.danzx.xof.core.filter.dsl

import com.github.danzx.xof.core.filter.PostsFilter

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class PostsFilterDslTest : StringSpec({

    "postsWith DSL should build PostsFilter object" {
        val expected = PostsFilter(userId = 1, titleQuery = "tle")
        val actual = postsWith {
            userId eq expected.userId
            title containing expected.titleQuery
        }

        actual shouldBe expected
    }
})