package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.empty
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.predicates
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.parentIdEquals
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.postIdEquals
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.userIdEquals

fun CommentsFilter.toSpecification() = predicates().and(userIdEqualsSpec).and(postIdEqualsSpec).and(parentIdEqualsSpec)

private val CommentsFilter.userIdEqualsSpec
    get() = userId?.let { userIdEquals(it) } ?: empty

private val CommentsFilter.postIdEqualsSpec
    get() = postId?.let { postIdEquals(it) } ?: empty

private val CommentsFilter.parentIdEqualsSpec
    get() = parentId?.let { parentIdEquals(it) } ?: empty
