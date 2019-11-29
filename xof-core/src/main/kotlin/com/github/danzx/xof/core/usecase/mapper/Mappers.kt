package com.github.danzx.xof.core.usecase.mapper

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Ids
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.domain.Usernames
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand

import java.time.LocalDateTime.now

fun CreateNewCommentCommand.toComment() = Comment(
    id = Ids.AUTO_GENERATED,
    content = content,
    postId = postId,
    parentId = parentId,
    created = now(),
    updated = now(),
    votes = 0,
    user = SimpleUser(
        id = userId,
        username = Usernames.NOT_AVAILABLE
    )
)

fun CreateNewPostCommand.toPost() = Post(
    id = Ids.AUTO_GENERATED,
    title = title,
    content = content,
    created = now(),
    updated = now(),
    votes = 0,
    user = SimpleUser(
        id = userId,
        username = Usernames.NOT_AVAILABLE
    )
)

fun CreateNewUserCommand.toUser() = User(
    id = Ids.AUTO_GENERATED,
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl,
    join = now()
)

infix fun ReplaceUserCommand.copyTo(user: User) {
    user.name = name
    user.lastName = lastName
    user.username = username
    user.avatarImageUrl = avatarImageUrl
}
