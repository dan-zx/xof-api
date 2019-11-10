package com.github.danzx.xof.core.repository.comment

interface CommentVotesCounter {
    fun countVotesById(id: Long): Long
}
