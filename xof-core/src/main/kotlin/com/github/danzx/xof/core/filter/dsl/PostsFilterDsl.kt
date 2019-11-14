package com.github.danzx.xof.core.filter.dsl

import com.github.danzx.xof.core.filter.PostsFilter

class PostsFilterBuilder {
    private var userId: Long? = null
    private var titleQuery: String? = null

    infix fun userId.eq(v: Long?) {
        userId = v
    }

    infix fun title.containing(v: String?) {
        titleQuery = v
    }

    fun build() = PostsFilter(userId, titleQuery)
}

fun postsWith(filters: PostsFilterBuilder.() -> Unit): PostsFilter {
    val builder = PostsFilterBuilder()
    builder.filters()
    return builder.build()
}