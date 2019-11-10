package com.github.danzx.xof.core.usecase.post.command

data class CreateNewPostCommand(
    var title: String,
    var content: String,
    var userId: Long
)