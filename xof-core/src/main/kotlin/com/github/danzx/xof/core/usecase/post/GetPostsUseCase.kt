package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.post.mapper.toLoaderFilter
import com.github.danzx.xof.core.repository.post.PaginatedPostsLoader

import javax.inject.Inject

@UseCaseComponent
class GetPostsUseCase : UseCase<PostsLoaderCommand, Page<Post>> {

    @Inject
    lateinit var loader : PaginatedPostsLoader

    override operator fun invoke(command: PostsLoaderCommand) =
        loader.loadPaginated(command.filter.toLoaderFilter(), command.pagination, command.sorting)
}