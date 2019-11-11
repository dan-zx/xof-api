package com.github.danzx.xof.core.usecase.post.mapper

import com.github.danzx.xof.core.domain.AUTO_GENERATED_ID
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.dataprovider.post.PaginatedPostsLoader

import java.time.LocalDateTime.now

fun CreateNewPostCommand.toNewPost() = Post(
    id = AUTO_GENERATED_ID,
    title = title,
    content = content,
    created = now(),
    updated = now(),
    votes = 0,
    user = SimpleUser(
        id = userId,
        username = "[Not available]"
    )
)

fun PostsLoaderCommand.Filter.toLoaderFilter() = PaginatedPostsLoader.Filter(
    userId = userId,
    titleQuery = titleQuery
)