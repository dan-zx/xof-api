package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.comment.CommentByIdLoader
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

class GetCommentByIdUseCase : UseCase<Long, Comment> {

    @Inject lateinit var loader : CommentByIdLoader

    override operator fun invoke(id: Long) = loader.loadById(id) ?: throw CommentNotFoundException()
}