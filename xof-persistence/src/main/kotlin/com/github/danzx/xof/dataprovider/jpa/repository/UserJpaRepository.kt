package com.github.danzx.xof.dataprovider.jpa.repository

import com.github.danzx.xof.dataprovider.jpa.entity.UserJpaEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserJpaRepository : JpaRepository<UserJpaEntity, Long>, JpaSpecificationExecutor<UserJpaEntity> {
    fun existsByUsername(username: String) : Boolean
    fun findByUsername(username: String) : UserJpaEntity?
}