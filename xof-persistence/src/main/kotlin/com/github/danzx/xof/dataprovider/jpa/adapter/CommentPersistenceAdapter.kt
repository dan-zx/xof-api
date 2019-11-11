package com.github.danzx.xof.dataprovider.jpa.adapter

import com.github.danzx.xof.common.Pagination
import com.github.danzx.xof.common.SortSpec
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.dataprovider.comment.CommentByIdLoader
import com.github.danzx.xof.core.dataprovider.comment.CommentByIdRemover
import com.github.danzx.xof.core.dataprovider.comment.CommentIdChecker
import com.github.danzx.xof.core.dataprovider.comment.CommentPersister
import com.github.danzx.xof.core.dataprovider.comment.CommentUpdater
import com.github.danzx.xof.core.dataprovider.comment.PaginatedCommentsLoader
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.DomainPage
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toComment
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toCommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toDomainPage
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.toSpecification
import com.github.danzx.xof.dataprovider.jpa.adapter.mapper.with
import com.github.danzx.xof.dataprovider.jpa.entity.CommentJpaEntity
import com.github.danzx.xof.dataprovider.jpa.repository.CommentJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.CommentVoteJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.PostJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.UserJpaRepository
import com.github.danzx.xof.dataprovider.jpa.repository.countVotesByCommentId

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class CommentPersistenceAdapter : CommentPersister, PaginatedCommentsLoader, CommentByIdLoader, CommentIdChecker, CommentUpdater, CommentByIdRemover {

    @Autowired
    lateinit var commentJpaRepository: CommentJpaRepository

    @Autowired
    lateinit var commentVoteJpaRepository: CommentVoteJpaRepository

    @Autowired
    lateinit var postJpaRepository: PostJpaRepository

    @Autowired
    lateinit var userJpaRepository: UserJpaRepository

    override fun save(comment: Comment): Comment {
        var commentJpaEntity = comment.toCommentJpaEntity()
        commentJpaEntity = commentJpaRepository.save(commentJpaEntity)
        comment.id = commentJpaEntity.id!!
        return comment
    }

    override fun loadPaginated(
        filter: PaginatedCommentsLoader.Filter,
        pagination: Pagination,
        sorting: List<SortSpec>
    ): DomainPage<Comment> =
        commentJpaRepository.findAll(filter.toSpecification(), pagination with sorting).map { it.toComment() }.toDomainPage()

    override fun loadById(id: Long) = commentJpaRepository.findByIdOrNull(id)?.toComment()

    override fun existsId(id: Long) = commentJpaRepository.existsById(id)

    override fun update(comment: Comment) = save(comment)

    override fun removeById(id: Long) = commentJpaRepository.deleteById(id)

    private fun countVotesById(id: Long) = commentVoteJpaRepository.countVotesByCommentId(id)

    private fun CommentJpaEntity.toComment() = this.toComment(countVotesById(this.id!!))

    private fun Comment.toCommentJpaEntity(): CommentJpaEntity {
        val post = postJpaRepository.findByIdOrNull(this.postId)!!
        val user = userJpaRepository.findByIdOrNull(this.user.id)!!
        val parentComment = if (this.parentId != null) commentJpaRepository.findByIdOrNull(this.parentId) else null
        return this.toCommentJpaEntity(post, user, parentComment)
    }
}