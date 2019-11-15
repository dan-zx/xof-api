package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.CommentVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.PostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.UserJpaEntity

fun Vote.toPostVoteJpaEntity(post: PostJpaEntity, user: UserJpaEntity) = PostVoteJpaEntity(
    id = toPostVoteJpaEntityId(),
    direction = Vote.Direction.ZERO,
    post = post,
    user = user
)

fun Vote.toPostVoteJpaEntityId() = PostVoteJpaEntity.Id(
    postId = entityId,
    userId = userId
)

fun Vote.toCommentVoteJpaEntity(comment: CommentJpaEntity, user: UserJpaEntity) = CommentVoteJpaEntity(
    id = toCommentVoteJpaEntityId(),
    direction = Vote.Direction.ZERO,
    comment = comment,
    user = user
)

fun Vote.toCommentVoteJpaEntityId() = CommentVoteJpaEntity.Id(
    commentId = entityId,
    userId = userId
)
