package com.github.danzx.xof.dataprovider.jpa.repository

import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface CommentJpaRepository : JpaRepository<CommentJpaEntity, Long>, JpaSpecificationExecutor<CommentJpaEntity>
