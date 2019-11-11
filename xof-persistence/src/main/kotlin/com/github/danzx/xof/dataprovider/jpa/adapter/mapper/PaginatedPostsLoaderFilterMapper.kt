package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.repository.post.PaginatedPostsLoader
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications

fun PaginatedPostsLoader.Filter.toSpecification(): Specification<PostJpaEntity> = Specifications.where(userIdEqualsSpec).and(titleContainsSpec)

private val PaginatedPostsLoader.Filter.userIdEqualsSpec
    get() = userId?.let { PostJpaSpecifications.userIdEquals(it) }

private val PaginatedPostsLoader.Filter.titleContainsSpec
    get() = titleQuery?.let { PostJpaSpecifications.titleContains(it) }
