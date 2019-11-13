package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.CommentVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.PostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.UserJpaEntity

fun Vote.toPostVoteJpaEntity(post: PostJpaEntity, user: UserJpaEntity) = PostVoteJpaEntity(
    toPostVoteJpaEntityId(),
    Vote.Direction.ZERO,
    post,
    user
)

fun Vote.toPostVoteJpaEntityId() = PostVoteJpaEntity.Id(
    entityId,
    userId
)

fun Vote.toCommentVoteJpaEntity(comment: CommentJpaEntity, user: UserJpaEntity) = CommentVoteJpaEntity(
    toCommentVoteJpaEntityId(),
    Vote.Direction.ZERO,
    comment,
    user
)

fun Vote.toCommentVoteJpaEntityId() = CommentVoteJpaEntity.Id(
    entityId,
    userId
)
