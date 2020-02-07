package com.github.danzx.xof.core.usecase.post.command

data class CreateNewPostCommand(
    val title: String,
    val content: String,
    val userId: Long)