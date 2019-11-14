package com.github.danzx.xof.core.usecase.comment.command

import com.github.danzx.xof.common.pagination.Pagination
import com.github.danzx.xof.common.sort.SortSpec
import com.github.danzx.xof.core.filter.CommentsFilter

data class CommentsLoaderCommand(
    val filter: CommentsFilter = CommentsFilter(),
    val pagination: Pagination = Pagination(),
    val sorting: List<SortSpec> = emptyList())
