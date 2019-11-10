package com.github.danzx.xof.core.repository.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.repository.PaginatedDataLoader

interface PaginatedPostsLoader : PaginatedDataLoader<PaginatedPostsLoader.Filter, Post> {

    data class Filter(
        val userId: Long? = null,
        val titleQuery: String? = null
    )
}

