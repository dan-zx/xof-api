package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.dataprovider.jpa.entity.UserJpaEntity

fun User.toUserJpaEntity() = UserJpaEntity(
    id = id.orAutoGeneratedId(),
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl,
    join = join
)

fun UserJpaEntity.toUser() = User(
    id = id!!,
    name = name,
    lastName = lastName,
    username = username,
    avatarImageUrl = avatarImageUrl,
    join = join
)
