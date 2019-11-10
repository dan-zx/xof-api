package com.github.danzx.xof.core.usecase.post.command

import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

data class PostsLoaderCommand(
    val filter: Filter = Filter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList()) {

    data class Filter(
        val userId: Long? = null,
        val titleQuery: String? = null
    )
}
