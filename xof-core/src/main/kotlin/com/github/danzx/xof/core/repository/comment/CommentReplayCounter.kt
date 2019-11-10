package com.github.danzx.xof.core.repository.comment

interface CommentReplayCounter {
    fun countRepliesById(id: Long): Long
}