package com.github.danzx.xof.core.dataprovider.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.dataprovider.PaginatedDataLoader

interface PaginatedPostsLoader : PaginatedDataLoader<PaginatedPostsLoader.Filter, Post> {

    data class Filter(
        val userId: Long? = null,
        val titleQuery: String? = null
    )
}

