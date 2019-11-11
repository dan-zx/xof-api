package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.common.Pagination
import com.github.danzx.xof.common.SortSpec
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.repository.post.PaginatedPostsLoader
import com.github.danzx.xof.core.repository.post.PostByIdLoader
import com.github.danzx.xof.core.repository.post.PostByIdRemover
import com.github.danzx.xof.core.repository.post.PostIdChecker
import com.github.danzx.xof.core.repository.post.PostPersister
import com.github.danzx.xof.core.repository.post.PostUpdater
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.DomainPage
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toDomainPage
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toPost
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toPostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toSpecification
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.with
import com.github.danzx.xof.dataprovider.jpa.entity.PostJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.PostJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.PostVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.countVotesByPostId

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PostPersistenceAdapter : PostPersister, PaginatedPostsLoader, PostByIdLoader, PostIdChecker, PostUpdater, PostByIdRemover {

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var postVoteJpaRepository: PostVoteJpaRepository

    @Autowired
    lateinit var userJpaRepository: UserJpaRepository

    override fun save(post: Post): Post {
        var postJpaEntity = post.toPostJpaEntity()
        postJpaEntity = postJpaRepository.save(postJpaEntity)
        post.id = postJpaEntity.id!!
        return post
    }

    override fun loadPaginated(
        filter: PaginatedPostsLoader.Filter,
        pagination: Pagination,
        sorting: List<SortSpec>
    ): DomainPage<Post> =
        postJpaRepository.findAll(filter.toSpecification(), pagination with sorting).map { it.toPost() }.toDomainPage()

    override fun loadById(id: Long) = postJpaRepository.findByIdOrNull(id)?.toPost()

    override fun existsId(id: Long) = postJpaRepository.existsById(id)

    override fun update(post: Post) = save(post)

    override fun removeById(id: Long) = postJpaRepository.deleteById(id)

    private fun countVotesByPostId(id: Long) = postVoteJpaRepository.countVotesByPostId(id)

    private fun PostJpaEntity.toPost() = this.toPost(countVotesByPostId(this.id!!))

    private fun Post.toPostJpaEntity() : PostJpaEntity {
        val user = userJpaRepository.findByIdOrNull(this.user.id)!!
        return this.toPostJpaEntity(user)
    }
}