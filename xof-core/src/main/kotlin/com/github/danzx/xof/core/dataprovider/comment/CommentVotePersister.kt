package com.github.danzx.xof.core.dataprovider.comment

import com.github.danzx.xof.core.domain.Vote

interface CommentVotePersister {
    fun saveOrUpdate(vote: Vote)
}