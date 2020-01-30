package com.github.danzx.xof.entrypoint.rest.request.mapper

import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.core.util.Pagination
import com.github.danzx.xof.entrypoint.rest.request.ContentUpdateRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.CreateUserRequest
import com.github.danzx.xof.entrypoint.rest.request.PaginationRequest
import com.github.danzx.xof.entrypoint.rest.request.ReplaceUserRequest

fun CreateUserRequest.toCreateNewUserCommand() = CreateNewUserCommand(
    name = name!!,
    lastName = lastName!!,
    username = username!!,
    avatarImageUrl = avatarImageUrl!!
)

fun ReplaceUserRequest.toReplaceUserCommand(id: Long) = ReplaceUserCommand(
    id = id,
    name = name!!,
    lastName = lastName!!,
    username = username!!,
    avatarImageUrl = avatarImageUrl!!
)

fun CreatePostRequest.toCreateNewPostCommand() = CreateNewPostCommand(
    title = title!!,
    content = content!!,
    userId = userId!!
)

fun CreateCommentRequest.toCreateNewCommentCommand() = CreateNewCommentCommand(
    content = content!!,
    userId = userId!!,
    postId = postId!!,
    parentId = parentId
)

fun PaginationRequest.toPagination() = Pagination(
    size,
    page
)

fun ContentUpdateRequest.toReplaceCommentContentCommand(id: Long) = ReplaceCommentContentCommand(
    id = id,
    value = this.value!!
)