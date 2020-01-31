package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.parentIdEquals
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.postIdEquals
import com.github.danzx.xof.dataprovider.jpa.repository.specification.CommentJpaSpecifications.userIdEquals

import org.springframework.data.jpa.domain.Specification.where

fun CommentsFilter.toSpecification() = where(userIdEqualsSpec)!!.and(postIdEqualsSpec)!!.and(parentIdEqualsSpec)!!

private val CommentsFilter.userIdEqualsSpec
    get() = userId?.let { userIdEquals(it) }

private val CommentsFilter.postIdEqualsSpec
    get() = postId?.let { postIdEquals(it) }

private val CommentsFilter.parentIdEqualsSpec
    get() = parentId?.let { parentIdEquals(it) }
