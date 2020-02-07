package com.github.danzx.xof.core.filter

data class CommentsFilter(
    val userId: Long? = null,
    val postId: Long? = null,
    val parentId: NullableValue<Long>? = null) {

    companion object {
        @JvmStatic val NONE = CommentsFilter()
    }
}


