package com.github.danzx.xof.dataprovider.jpa.view

import com.github.danzx.xof.core.domain.Vote

data class VoteCountView(
    var count: Long,
    var direction: Vote.Direction)
