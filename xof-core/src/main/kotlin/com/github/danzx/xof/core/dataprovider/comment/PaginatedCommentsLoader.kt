package com.github.danzx.xof.core.dataprovider.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.dataprovider.PaginatedDataLoader
import com.github.danzx.xof.core.filter.CommentsFilter

interface PaginatedCommentsLoader : PaginatedDataLoader<CommentsFilter, Comment>
