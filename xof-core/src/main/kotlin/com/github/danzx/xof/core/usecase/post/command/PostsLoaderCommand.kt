package com.github.danzx.xof.core.usecase.post.command

import com.github.danzx.xof.common.pagination.Pagination
import com.github.danzx.xof.common.sort.SortSpec
import com.github.danzx.xof.core.filter.PostsFilter

data class PostsLoaderCommand(
    val filter: PostsFilter = PostsFilter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList())
