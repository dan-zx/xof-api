package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.dataprovider.jpa.entity.PostVoteJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.PostJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.PostVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository
import com.github.danzx.xof.dataprovider.jpa.test.TEST_POST
import com.github.danzx.xof.dataprovider.jpa.test.TEST_USER

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.jdbc.Sql

class PostVotePersistenceAdapterTest : PersistenceAdapterBaseTest() {

    @Autowired lateinit var postVoteJpaRepository: PostVoteJpaRepository
    @Autowired lateinit var postJpaRepository: PostJpaRepository
    @Autowired lateinit var userJpaRepository: UserJpaRepository

    lateinit var adapter: PostVotePersistenceAdapter

    @BeforeEach
    fun onBeforeTest() {
        adapter = PostVotePersistenceAdapter()
        adapter.postVoteJpaRepository = postVoteJpaRepository
        adapter.postJpaRepository = postJpaRepository
        adapter.userJpaRepository = userJpaRepository
    }

    @Test
    @Sql("classpath:test-user.sql",
         "classpath:test-post.sql")
    fun `should save post vote when vote does not exist`() {
        val id = PostVoteJpaEntity.Id(TEST_POST.id, TEST_USER.id)
        val vote = Vote(id.postId, id.userId, Vote.Direction.UP)
        var entity = postVoteJpaRepository.findByIdOrNull(id)

        entity shouldBe null

        adapter.saveOrUpdate(vote)

        entity = postVoteJpaRepository.findByIdOrNull(PostVoteJpaEntity.Id(TEST_POST.id, TEST_USER.id))

        entity shouldNotBe null
        entity!!.direction shouldBe vote.direction
    }

    @Test
    @Sql("classpath:test-user.sql",
         "classpath:test-post.sql",
         "classpath:test-post-vote.sql")
    fun `should update post vote when vote exists`() {
        val id = PostVoteJpaEntity.Id(TEST_POST.id, TEST_USER.id)
        val vote = Vote(id.postId, id.userId, Vote.Direction.DOWN)
        var entity = postVoteJpaRepository.findByIdOrNull(id)

        entity shouldNotBe null
        entity!!.direction shouldNotBe vote.direction

        adapter.saveOrUpdate(vote)

        entity = postVoteJpaRepository.findByIdOrNull(PostVoteJpaEntity.Id(TEST_POST.id, TEST_USER.id))

        entity shouldNotBe null
        entity!!.direction shouldBe vote.direction
    }
}