package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.exception.CommentNotFoundException
import com.github.danzx.xof.core.repository.comment.CommentIdChecker
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

@UseCaseComponent
class ValidateCommentIdExistsUseCase : UseCase<Long, Unit> {

    @Inject
    lateinit var checker: CommentIdChecker

    override operator fun invoke(id: Long) {
        if (!checker.existsId(id)) throw CommentNotFoundException()
    }
}