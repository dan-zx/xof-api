package com.github.danzx.xof.core.dataprovider

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.filter.CommentsFilter

interface CommentByIdLoader : DataByIdLoader<Long, Comment>
interface CommentByIdRemover : DataByIdRemover<Long>
interface CommentIdChecker : DataIdChecker<Long>
interface CommentPersister : DataPersister<Comment>
interface CommentUpdater : DataUpdater<Comment>
interface PaginatedCommentsLoader : PaginatedDataLoader<CommentsFilter, Comment>

interface CommentVotePersister {
    fun saveOrUpdate(vote: Vote)
}
