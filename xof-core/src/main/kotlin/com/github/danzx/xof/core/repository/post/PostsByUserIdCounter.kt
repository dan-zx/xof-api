package com.github.danzx.xof.core.repository.post

interface PostsByUserIdCounter {
    fun countByUserId(userId: Long): Long
}