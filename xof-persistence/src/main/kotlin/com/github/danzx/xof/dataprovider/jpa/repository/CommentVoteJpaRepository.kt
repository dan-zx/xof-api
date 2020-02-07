package com.github.danzx.xof.dataprovider.jpa.repository

import com.github.danzx.xof.dataprovider.jpa.view.VoteCountView
import com.github.danzx.xof.dataprovider.jpa.entity.CommentVoteJpaEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface CommentVoteJpaRepository: JpaRepository<CommentVoteJpaEntity, CommentVoteJpaEntity.Id>, JpaSpecificationExecutor<CommentVoteJpaEntity> {

    @Query(
        "select new com.github.danzx.xof.dataprovider.jpa.view.VoteCountView(count(votes), votes.direction) " +
            "from CommentVote votes " +
            "where votes.id.commentId = ?1 " +
            "group by votes.direction")
    fun countByCommentIdGroupingByDirection(commentId: Long): List<VoteCountView>
}
