package com.github.danzx.xof.core.usecase.comment.mapper

import com.github.danzx.xof.core.domain.AUTO_GENERATED_ID
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.repository.comment.PaginatedCommentsLoader
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand

import java.time.LocalDateTime.now

fun CreateNewCommentCommand.toNewComment() = Comment(
    id = AUTO_GENERATED_ID,
    content = content,
    postId = postId,
    parentId = parentId,
    created = now(),
    updated = now(),
    votes = 0,
    user = SimpleUser(
        id = userId,
        username = "[Not available]"
    )
)

fun CommentsLoaderCommand.Filter.toLoaderFilter() = PaginatedCommentsLoader.Filter(
    userId = userId,
    parentId = parentId,
    postId = postId
)