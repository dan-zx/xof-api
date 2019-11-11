package com.github.danzx.xof.dataprovider.jpa.repository

import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PostJpaRepository : JpaRepository<PostJpaEntity, Long>, JpaSpecificationExecutor<PostJpaEntity> {
    fun countByUserId(userId: Long): Long
}