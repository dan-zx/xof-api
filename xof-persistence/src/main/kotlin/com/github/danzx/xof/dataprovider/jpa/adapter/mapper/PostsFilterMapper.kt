package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.PostsFilter
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.empty
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.predicates
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.titleContains
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.userIdEquals

fun PostsFilter.toSpecification() = predicates().and(userIdEqualsSpec).and(titleContainsSpec)

private val PostsFilter.userIdEqualsSpec
    get() = userId?.let { userIdEquals(it) } ?: empty

private val PostsFilter.titleContainsSpec
    get() = titleQuery?.let { titleContains(it) } ?: empty
