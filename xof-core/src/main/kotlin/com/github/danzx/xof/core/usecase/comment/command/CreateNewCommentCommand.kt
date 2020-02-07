package com.github.danzx.xof.core.usecase.comment.command

data class CreateNewCommentCommand(
    val content: String,
    val userId: Long,
    val postId: Long,
    val parentId: Long?)