package com.github.danzx.xof.core.usecase.post.command

import com.github.danzx.xof.core.filter.PostsFilter
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

data class PostsLoaderCommand(
    val filter: PostsFilter = PostsFilter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList())
