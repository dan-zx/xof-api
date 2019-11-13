package com.github.danzx.xof.core.filter

data class CommentsFilter(
    val userId: Long? = null,
    val postId: Long? = null,
    val parentId: Long? = null)


