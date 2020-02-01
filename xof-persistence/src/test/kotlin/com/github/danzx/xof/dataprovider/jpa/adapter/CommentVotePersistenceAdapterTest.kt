package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.dataprovider.jpa.entity.CommentVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.CommentJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.CommentVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository
import com.github.danzx.xof.dataprovider.jpa.test.TEST_COMMENT
import com.github.danzx.xof.dataprovider.jpa.test.TEST_USER

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql

class CommentVotePersistenceAdapterTest : PersistenceAdapterBaseTest() {

    @Autowired lateinit var commentVoteJpaRepository: CommentVoteJpaRepository
    @Autowired lateinit var commentJpaRepository: CommentJpaRepository
    @Autowired lateinit var userJpaRepository: UserJpaRepository

    lateinit var adapter: CommentVotePersistenceAdapter

    @BeforeEach
    fun onBeforeTest() {
        adapter = CommentVotePersistenceAdapter()
        adapter.commentVoteJpaRepository = commentVoteJpaRepository
        adapter.commentJpaRepository = commentJpaRepository
        adapter.userJpaRepository = userJpaRepository
    }

    @Test
    @Sql("classpath:test-user.sql",
         "classpath:test-post.sql",
         "classpath:test-comment.sql")
    fun `should save comment vote when vote does not exist`() {
        val id = CommentVoteJpaEntity.Id(TEST_COMMENT.id, TEST_USER.id)
        val vote = Vote(id.commentId, id.userId, Vote.Direction.UP)
        var entity = commentVoteJpaRepository.findByIdOrNull(id)

        entity shouldBe null

        adapter.saveOrUpdate(vote)

        entity = commentVoteJpaRepository.findByIdOrNull(CommentVoteJpaEntity.Id(TEST_COMMENT.id, TEST_USER.id))

        entity shouldNotBe null
        entity!!.direction shouldBe vote.direction
    }

    @Test
    @Sql("classpath:test-user.sql",
         "classpath:test-post.sql",
         "classpath:test-comment.sql",
         "classpath:test-comment-vote.sql")
    fun `should update comment vote when vote exists`() {
        val id = CommentVoteJpaEntity.Id(TEST_COMMENT.id, TEST_USER.id)
        val vote = Vote(id.commentId, id.userId, Vote.Direction.UP)
        var entity = commentVoteJpaRepository.findByIdOrNull(id)

        entity shouldNotBe null
        entity!!.direction shouldNotBe vote.direction

        adapter.saveOrUpdate(vote)

        entity = commentVoteJpaRepository.findByIdOrNull(CommentVoteJpaEntity.Id(TEST_COMMENT.id, TEST_USER.id))

        entity shouldNotBe null
        entity!!.direction shouldBe vote.direction
    }
}