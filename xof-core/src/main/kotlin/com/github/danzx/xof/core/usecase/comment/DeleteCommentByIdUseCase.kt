package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.dataprovider.CommentByIdRemover
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

class DeleteCommentByIdUseCase : UseCase<Long, Unit> {

    @Inject lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase
    @Inject lateinit var remover: CommentByIdRemover

    override operator fun invoke(id: Long) {
        validateCommentIdExistsUseCase(id)
        remover.removeById(id)
    }
}