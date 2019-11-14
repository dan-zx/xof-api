package com.github.danzx.xof.entrypoint.rest.adapter

import com.github.danzx.xof.common.pagination.Pagination
import com.github.danzx.xof.common.sort.dsl.sortBy
import com.github.danzx.xof.core.filter.CommentsFilter
import com.github.danzx.xof.core.usecase.comment.CreateNewCommentUseCase
import com.github.danzx.xof.core.usecase.comment.DeleteCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentsUseCase
import com.github.danzx.xof.core.usecase.comment.ReplaceCommentContentUseCase
import com.github.danzx.xof.core.usecase.comment.VoteOnCommentUseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand
import com.github.danzx.xof.entrypoint.rest.mapper.toCreateNewCommentCommand
import com.github.danzx.xof.entrypoint.rest.mapper.toPageResponse
import com.github.danzx.xof.entrypoint.rest.mapper.toVote
import com.github.danzx.xof.entrypoint.rest.request.CreateCommentRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class CommentUseCaseAdapter {

    @Autowired lateinit var createNewCommentUseCase: CreateNewCommentUseCase
    @Autowired lateinit var getCommentByIdUseCase: GetCommentByIdUseCase
    @Autowired lateinit var getCommentsUseCase: GetCommentsUseCase
    @Autowired lateinit var replaceCommentContentUseCase: ReplaceCommentContentUseCase
    @Autowired lateinit var voteOnCommentUseCase: VoteOnCommentUseCase
    @Autowired lateinit var deleteCommentByIdUseCase: DeleteCommentByIdUseCase

    fun create(request: CreateCommentRequest) = createNewCommentUseCase(request.toCreateNewCommentCommand())

    fun getById(id: Long) = getCommentByIdUseCase(id)

    fun getAll(filter: CommentsFilter, pagination: Pagination) =
        getCommentsUseCase(CommentsLoaderCommand(filter, pagination, sortBy { +"created" }))
            .toPageResponse()

    fun replaceContent(id: Long, content: String) = replaceCommentContentUseCase(ReplaceCommentContentCommand(id, content))

    fun vote(id: Long, request: VoteRequest) = voteOnCommentUseCase(request.toVote(id))

    fun deleteById(id: Long) = deleteCommentByIdUseCase(id)
}