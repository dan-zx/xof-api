package com.github.danzx.xof.dataprovider.jpa.repository

import com.github.danzx.xof.dataprovider.jpa.entity.PostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.view.VoteCountView

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface PostVoteJpaRepository: JpaRepository<PostVoteJpaEntity, PostVoteJpaEntity.Id>, JpaSpecificationExecutor<PostVoteJpaEntity> {

    @Query(
        "select new com.github.danzx.xof.dataprovider.jpa.view.VoteCountView(count(votes), votes.direction) " +
           "from PostVote votes " +
           "where votes.id.postId = ?1 " +
           "group by votes.direction")
    fun countByPostIdGroupingByDirection(postId: Long): List<VoteCountView>
}
