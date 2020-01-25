package com.github.danzx.xof.dataprovider.jpa.repository.specification

import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity

import org.springframework.data.jpa.domain.Specification

object PostJpaSpecifications : JpaEntitySpecifications<PostJpaEntity> {

    fun userIdEquals(userId: Long): Specification<PostJpaEntity> {
        return Specification { root, _, cb -> cb.equal(root.get<String>("user").get<String>("id"), userId) }
    }

    fun titleContains(titleQuery: String): Specification<PostJpaEntity> {
        return Specification { root, _, cb -> cb.like(cb.upper(root.get("title")), titleQuery.toSqlContainsValue()) }
    }

    private fun String.toSqlContainsValue() = "%%%s%%".format(this.toUpperCase())
}