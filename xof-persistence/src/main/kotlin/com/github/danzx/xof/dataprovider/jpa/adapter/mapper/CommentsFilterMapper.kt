package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications

fun CommentsFilter.toSpecification(): Specification<CommentJpaEntity> = Specifications.where(userIdEqualsSpec).and(postIdEqualsSpec).and(parentIdEqualsSpec)

private val CommentsFilter.userIdEqualsSpec
    get() = userId?.let { CommentJpaSpecifications.userIdEquals(it) }

private val CommentsFilter.postIdEqualsSpec
    get() = postId?.let { CommentJpaSpecifications.postIdEquals(it) }

private val CommentsFilter.parentIdEqualsSpec
    get() = parentId?.let { CommentJpaSpecifications.parentIdEquals(it) }
