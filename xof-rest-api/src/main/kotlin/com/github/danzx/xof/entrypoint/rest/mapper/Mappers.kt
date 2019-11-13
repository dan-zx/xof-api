package com.github.danzx.xof.entrypoint.rest.mapper

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest

fun CreateUserRequest.toCreateNewUserCommand() = CreateNewUserCommand(
    name,
    lastName,
    username,
    avatarImageUrl
)

fun ReplaceUserRequest.toReplaceUserCommand(id: Long) = ReplaceUserCommand(
    id,
    name,
    lastName,
    username,
    avatarImageUrl
)

fun CreatePostRequest.toCreateNewPostCommand() = CreateNewPostCommand(
    title,
    content,
    userId
)

fun VoteRequest.toVote(entityId: Long) = Vote(
    entityId,
    userId,
    direction
)

fun CreateCommentRequest.toCreateNewCommentCommand() = CreateNewCommentCommand(
    content,
    userId,
    postId,
    parentId
)