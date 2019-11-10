package com.github.danzx.xof.core.usecase.comment.command

import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

data class CommentsLoaderCommand(
    val filter: Filter = Filter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList()) {

    data class Filter(
        val userId: Long? = null,
        val postId: Long? = null,
        val parentId: Long? = null
    )
}
