package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.dataprovider.comment.CommentVotePersister
import com.github.danzx.xof.dataprovider.jpa.repository.CommentVoteJpaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class CommentVotePersistenceAdapter : CommentVotePersister {

    @Autowired
    lateinit var commentVoteJpaRepository: CommentVoteJpaRepository

    override fun saveOrUpdate(vote: Vote) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}