package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.core.repository.comment.PaginatedCommentsLoader
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.mapper.toLoaderFilter

import javax.inject.Inject

@UseCaseComponent
class GetCommentsFilteredUseCase : UseCase<CommentsLoaderCommand, Page<Comment>> {

    @Inject
    lateinit var loader: PaginatedCommentsLoader

    override operator fun invoke(command: CommentsLoaderCommand) =
        loader.loadPaginated(command.filter.toLoaderFilter(), command.pagination, command.sorting)
}