package com.github.danzx.xof.core.repository.comment

import com.github.danzx.xof.core.domain.Vote

interface CommentVotePersister {
    fun saveOrUpdate(vote: Vote)
}