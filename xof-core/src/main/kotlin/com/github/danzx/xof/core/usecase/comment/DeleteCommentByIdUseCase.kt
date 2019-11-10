package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.repository.comment.CommentByIdRemover
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

@UseCaseComponent
class DeleteCommentByIdUseCase : UseCase<Long, Unit> {

    @Inject
    lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase

    @Inject
    lateinit var remover: CommentByIdRemover

    override operator fun invoke(id: Long) {
        validateCommentIdExistsUseCase(id)
        remover.removeById(id)
    }
}