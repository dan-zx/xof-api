package com.github.danzx.xof.core.filter

object title
object userId
object postId
object parentId

class CommentsFilterBuilder {
    private var userId: Long? = null
    private var postId: Long? = null
    private var parentId: Long? = null

    infix fun userId.eq(v: Long?) {
        userId = v
    }

    infix fun postId.eq(v: Long?) {
        postId = v
    }


    infix fun parentId.eq(v: Long?) {
        parentId = v
    }

    fun build() = CommentsFilter(userId, postId, parentId)
}

fun commentsWith(filters: CommentsFilterBuilder.() -> Unit): CommentsFilter {
    val builder = CommentsFilterBuilder()
    builder.filters()
    return builder.build()
}

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