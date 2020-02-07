package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.dataprovider.CommentVotePersister
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toCommentVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toCommentVoteJpaEntityId
import com.github.danzx.xof.dataprovider.jpa.entity.CommentVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.CommentJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.CommentVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentVotePersistenceAdapter : CommentVotePersister {

    @Autowired lateinit var commentVoteJpaRepository: CommentVoteJpaRepository
    @Autowired lateinit var commentJpaRepository: CommentJpaRepository
    @Autowired lateinit var userJpaRepository: UserJpaRepository

    override fun saveOrUpdate(vote: Vote) {
        val commentVoteJpaEntity = findById(vote.toCommentVoteJpaEntityId()) ?: vote.toCommentVoteJpaEntity()
        commentVoteJpaEntity.direction = vote.direction
        commentVoteJpaRepository.save(commentVoteJpaEntity)
    }

    private fun findById(id: CommentVoteJpaEntity.Id) = commentVoteJpaRepository.findByIdOrNull(id)

    private fun Vote.toCommentVoteJpaEntity(): CommentVoteJpaEntity {
        val comment = commentJpaRepository.findByIdOrNull(this.entityId)!!
        val user = userJpaRepository.findByIdOrNull(this.userId)!!
        return this.toCommentVoteJpaEntity(comment, user)
    }
}
