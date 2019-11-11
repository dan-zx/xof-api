package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.repository.post.PostVotePersister
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toPostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toPostVoteJpaEntityId
import com.github.danzx.xof.dataprovider.jpa.entity.PostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.PostJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.PostVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostVotePersistenceAdapter : PostVotePersister {

    @Autowired
    lateinit var postVoteJpaRepository: PostVoteJpaRepository

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var userJpaRepository: UserJpaRepository

    override fun saveOrUpdate(vote: Vote) {
        val postVoteJpaEntity = findById(vote.toPostVoteJpaEntityId()) ?: vote.toPostVoteJpaEntity()
        postVoteJpaEntity.direction = vote.direction
        postVoteJpaRepository.save(postVoteJpaEntity)
    }

    private fun findById(id: PostVoteJpaEntity.Id) = postVoteJpaRepository.findByIdOrNull(id)

    private fun Vote.toPostVoteJpaEntity(): PostVoteJpaEntity {
        val post = postJpaRepository.findByIdOrNull(this.entityId)!!
        val user = userJpaRepository.findByIdOrNull(this.userId)!!
        return this.toPostVoteJpaEntity(post, user)
    }
}