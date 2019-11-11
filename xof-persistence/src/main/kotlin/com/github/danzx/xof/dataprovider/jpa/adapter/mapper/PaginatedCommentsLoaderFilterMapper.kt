package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.dataprovider.comment.PaginatedCommentsLoader
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications

fun PaginatedCommentsLoader.Filter.toSpecification(): Specification<CommentJpaEntity> = Specifications.where(userIdEqualsSpec).and(postIdEqualsSpec).and(parentIdEqualsSpec)

private val PaginatedCommentsLoader.Filter.userIdEqualsSpec
    get() = userId?.let { CommentJpaSpecifications.userIdEquals(it) }

private val PaginatedCommentsLoader.Filter.postIdEqualsSpec
    get() = postId?.let { CommentJpaSpecifications.postIdEquals(it) }

private val PaginatedCommentsLoader.Filter.parentIdEqualsSpec
    get() = parentId?.let { CommentJpaSpecifications.parentIdEquals(it) }
