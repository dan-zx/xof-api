package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.SimpleUser
import com.github.danzx.xof.core.domain.orAutoGeneratedId
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.entity.UserJpaEntity

fun Comment.toCommentJpaEntity(post: PostJpaEntity, user: UserJpaEntity, parentComment: CommentJpaEntity? = null) = CommentJpaEntity(
    id = id.orAutoGeneratedId(),
    content = content,
    created = created,
    updated = created,
    post = post,
    user = user,
    parentComment = parentComment
)

fun CommentJpaEntity.toComment(votes: Long) = Comment(
    id = id!!,
    content = content,
    created = created,
    updated = created,
    votes = votes,
    postId = post.id!!,
    parentId = parentComment?.id!!,
    user = SimpleUser(
        id = user.id!!,
        username = user.username
    )
)
