package com.github.danzx.xof.core.filter.dsl

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.core.filter.NullableValue

class CommentsFilterBuilder {
    private var userId: Long? = null
    private var postId: Long? = null
    private var parentId: NullableValue<Long>? = null

    infix fun userId.eq(v: Long?) {
        userId = v
    }

    infix fun postId.eq(v: Long?) {
        postId = v
    }

    infix fun parentId.eq(v: Long?) {
        parentId = NullableValue(v)
    }

    fun build() = CommentsFilter(userId, postId, parentId)
}

fun commentsWith(filters: CommentsFilterBuilder.() -> Unit): CommentsFilter {
    val builder = CommentsFilterBuilder()
    builder.filters()
    return builder.build()
}