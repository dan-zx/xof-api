package com.github.danzx.xof.core.domain

import java.time.LocalDateTime

data class Comment(
    var id: Long,
    var content: String,
    var created: LocalDateTime,
    var updated: LocalDateTime,
    var user: SimpleUser,
    var postId: Long,
    var parentId: Long?,
    var votes: Long)
