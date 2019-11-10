package com.github.danzx.xof.core.repository.post

interface PostVotesCounter {
    fun countVotesById(id: Long): Long
}
