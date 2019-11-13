package com.github.danzx.xof.core.dataprovider.post

import com.github.danzx.xof.core.domain.Vote

interface PostVotePersister {
    fun saveOrUpdate(vote: Vote)
}