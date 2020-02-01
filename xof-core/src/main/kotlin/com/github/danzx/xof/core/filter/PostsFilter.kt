package com.github.danzx.xof.core.filter

data class PostsFilter(
    val userId: Long? = null,
    val titleQuery: String? = null) {

    companion object {
        @JvmStatic val NONE = PostsFilter()
    }
}




