package com.github.danzx.xof.core.filter.dsl

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.core.filter.PostsFilter

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class CommentsFilterDslTest : StringSpec({

    "commentsWith DSL should build CommentsFilter object" {
        val expected = CommentsFilter(userId = 1, postId = 2, parentId = 3)
        val actual = commentsWith {
            userId eq expected.userId
            postId eq expected.postId
            parentId eq expected.parentId
        }

        actual shouldBe expected
    }

    "postsWith DSL should build PostsFilter object" {
        val expected = PostsFilter(userId = 1, titleQuery = "tle")
        val actual = postsWith {
            userId eq expected.userId
            title containing expected.titleQuery
        }

        actual shouldBe expected
    }
})