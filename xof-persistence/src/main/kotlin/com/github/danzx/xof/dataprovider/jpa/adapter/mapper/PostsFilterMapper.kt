package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.PostsFilter
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.domain.Specifications

fun PostsFilter.toSpecification(): Specification<PostJpaEntity> = Specifications.where(userIdEqualsSpec).and(titleContainsSpec)

private val PostsFilter.userIdEqualsSpec
    get() = userId?.let { PostJpaSpecifications.userIdEquals(it) }

private val PostsFilter.titleContainsSpec
    get() = titleQuery?.let { PostJpaSpecifications.titleContains(it) }
