package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.common.pagination.Page
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.post.PaginatedPostsLoader

import javax.inject.Inject

class GetPostsUseCase : UseCase<PostsLoaderCommand, Page<Post>> {

    @Inject lateinit var loader : PaginatedPostsLoader

    override operator fun invoke(command: PostsLoaderCommand) = loader.loadPaginated(command.filter, command.pagination, command.sorting)
}