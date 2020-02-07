package com.github.danzx.xof.core.dataprovider

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.filter.PostsFilter

interface PaginatedPostsLoader : PaginatedDataLoader<PostsFilter, Post>
interface PostByIdLoader : DataByIdLoader<Long, Post>
interface PostByIdRemover : DataByIdRemover<Long>
interface PostIdChecker : DataIdChecker<Long>
interface PostPersister : DataPersister<Post>
interface PostUpdater : DataUpdater<Post>

interface PostVotePersister {
    fun saveOrUpdate(vote: Vote)
}
