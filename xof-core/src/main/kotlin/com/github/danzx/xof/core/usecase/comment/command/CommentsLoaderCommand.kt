package com.github.danzx.xof.core.usecase.comment.command

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.core.util.SortSpec

data class CommentsLoaderCommand(
    val filter: CommentsFilter = CommentsFilter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList())
