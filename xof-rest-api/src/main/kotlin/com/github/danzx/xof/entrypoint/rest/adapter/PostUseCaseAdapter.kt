package com.github.danzx.xof.entrypoint.rest.adapter

import com.github.danzx.xof.common.pagination.Pagination
import com.github.danzx.xof.common.sort.sortBy
import com.github.danzx.xof.core.filter.PostsFilter
import com.github.danzx.xof.core.usecase.post.CreateNewPostUseCase
import com.github.danzx.xof.core.usecase.post.DeletePostByIdUseCase
import com.github.danzx.xof.core.usecase.post.GetPostByIdUseCase
import com.github.danzx.xof.core.usecase.post.GetPostsUseCase
import com.github.danzx.xof.core.usecase.post.ReplacePostContentUseCase
import com.github.danzx.xof.core.usecase.post.ReplacePostTitleUseCase
import com.github.danzx.xof.core.usecase.post.VoteOnPostUseCase
import com.github.danzx.xof.core.usecase.post.command.command
import com.github.danzx.xof.entrypoint.rest.mapper.toCreateNewPostCommand
import com.github.danzx.xof.entrypoint.rest.mapper.toPageResponse
import com.github.danzx.xof.entrypoint.rest.mapper.toVote
import com.github.danzx.xof.entrypoint.rest.request.CreatePostRequest
import com.github.danzx.xof.entrypoint.rest.request.VoteRequest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class PostUseCaseAdapter {

    @Autowired lateinit var createNewPostUseCase: CreateNewPostUseCase
    @Autowired lateinit var getPostByIdUseCase: GetPostByIdUseCase
    @Autowired lateinit var getPostsUseCase: GetPostsUseCase
    @Autowired lateinit var replacePostTitleUseCase: ReplacePostTitleUseCase
    @Autowired lateinit var replacePostContentUseCase: ReplacePostContentUseCase
    @Autowired lateinit var voteOnPostUseCase: VoteOnPostUseCase
    @Autowired lateinit var deletePostByIdUseCase: DeletePostByIdUseCase

    fun create(request: CreatePostRequest) = createNewPostUseCase(request.toCreateNewPostCommand())

    fun getById(id: Long) = getPostByIdUseCase(id)

    fun getAll(filter: PostsFilter, pagination: Pagination) =
        getPostsUseCase(command(filter, pagination, sortBy { +"created" }))
        .toPageResponse()

    fun replaceTitle(id: Long, title: String) = replacePostTitleUseCase(command(id, title))

    fun replaceContent(id: Long, content: String) = replacePostContentUseCase(command(id, content))

    fun vote(id: Long, request: VoteRequest) = voteOnPostUseCase(request.toVote(id))

    fun deleteById(id: Long) = deletePostByIdUseCase(id)
}