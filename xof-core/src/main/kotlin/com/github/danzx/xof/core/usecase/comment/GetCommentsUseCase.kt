package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.PaginatedCommentsLoader
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.util.Page

import javax.inject.Inject

class GetCommentsUseCase : UseCase<CommentsLoaderCommand, Page<Comment>> {

    @Inject lateinit var loader: PaginatedCommentsLoader

    override operator fun invoke(command: CommentsLoaderCommand) = loader.loadPaginated(command.filter, command.pagination, command.sorting)
}