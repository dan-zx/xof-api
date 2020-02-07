package com.github.danzx.xof.dataprovider.jpa.adapter.mapper

import com.github.danzx.xof.core.filter.PostsFilter
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.titleContains
import com.github.danzx.xof.dataprovider.jpa.repository.specification.PostJpaSpecifications.userIdEquals

import org.springframework.data.jpa.domain.Specification.where

fun PostsFilter.toSpecification() = where(userIdEqualsSpec)!!.and(titleContainsSpec)!!

private val PostsFilter.userIdEqualsSpec
    get() = userId?.let { userIdEquals(it) }

private val PostsFilter.titleContainsSpec
    get() = titleQuery?.let { titleContains(it) }
