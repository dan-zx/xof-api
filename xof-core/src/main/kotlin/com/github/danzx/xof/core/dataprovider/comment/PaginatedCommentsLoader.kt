package com.github.danzx.xof.core.dataprovider.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.dataprovider.PaginatedDataLoader

interface PaginatedCommentsLoader : PaginatedDataLoader<PaginatedCommentsLoader.Filter, Comment> {

    data class Filter(
        val userId: Long? = null,
        val postId: Long? = null,
        val parentId: Long? = null
    )
}

