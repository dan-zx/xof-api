package com.github.danzx.xof.core.filter.dsl

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.core.filter.NullableValue

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CommentsFilterDslTest : StringSpec({

    "commentsWith DSL should build CommentsFilter object" {
        val expected = CommentsFilter(userId = 1, postId = 2, parentId = NullableValue(3))
        val actual = commentsWith {
            userId eq expected.userId
            postId eq expected.postId
            parentId eq expected.parentId?.value
        }

        actual shouldBe expected
    }
})