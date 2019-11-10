package com.github.danzx.xof.core.repository.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.repository.PaginatedDataLoader

interface PaginatedCommentsLoader : PaginatedDataLoader<PaginatedCommentsLoader.Filter, Comment> {

    data class Filter(
        val userId: Long? = null,
        val postId: Long? = null,
        val parentId: Long? = null
    )
}

