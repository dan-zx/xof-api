package com.github.danzx.xof.core.usecase.comment.command

data class CreateNewCommentCommand(
    var content: String,
    var userId: Long,
    var postId: Long,
    var parentId: Long?
)