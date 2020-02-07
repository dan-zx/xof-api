package com.github.danzx.xof.dataprovider.jpa.repository.specification

import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity

import org.springframework.data.jpa.domain.Specification

object CommentJpaSpecifications {

    fun userIdEquals(userId: Long): Specification<CommentJpaEntity> {
        return Specification { root, _, cb -> cb.equal(root.get<String>("user").get<String>("id"), userId) }
    }

    fun postIdEquals(postId: Long): Specification<CommentJpaEntity> {
        return Specification { root, _, cb -> cb.equal(root.get<String>("post").get<String>("id"), postId) }
    }

    fun parentIdEquals(parentId: Long?) = if (parentId != null) parentIdEquals(parentId) else parentIdEqualsNull()

    private fun parentIdEquals(parentId: Long): Specification<CommentJpaEntity> {
        return Specification { root, _, cb -> cb.equal(root.get<String>("parentComment").get<String>("id"), parentId) }
    }

    private fun parentIdEqualsNull(): Specification<CommentJpaEntity> {
        return Specification { root, _, _ -> root.get<String>("parentComment").get<String>("id").isNull }
    }
}