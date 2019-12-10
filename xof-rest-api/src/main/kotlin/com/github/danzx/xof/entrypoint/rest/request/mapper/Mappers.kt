package com.github.danzx.xof.entrypoint.rest.request.mapper

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest

fun CreateUserRequest.toCreateNewUserCommand() = CreateNewUserCommand(
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl
)

fun ReplaceUserRequest.toReplaceUserCommand(id: Long) = ReplaceUserCommand(
    id = id,
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl
)

fun CreatePostRequest.toCreateNewPostCommand() = CreateNewPostCommand(
    title = title,
    content = content,
    userId = userId
)

fun VoteRequest.toVote(entityId: Long) = Vote(
    entityId = entityId,
    userId = userId,
    direction = direction
)

fun CreateCommentRequest.toCreateNewCommentCommand() = CreateNewCommentCommand(
    content = content,
    userId = userId,
    postId = postId,
    parentId = parentId
)

fun PaginationRequest.toPagination() = Pagination(
    page,
    size
)