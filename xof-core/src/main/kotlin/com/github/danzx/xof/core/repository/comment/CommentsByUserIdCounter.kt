package com.github.danzx.xof.core.repository.comment

interface CommentsByUserIdCounter {
    fun countByUserId(id: Long): Long
}