package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.repository.comment.CommentsByUserIdCounter
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

@UseCaseComponent
class CountCommentsByUserIdUseCase : UseCase<Long, Long> {

    @Inject
    lateinit var counter : CommentsByUserIdCounter

    override operator fun invoke(userId: Long) = counter.countByUserId(userId)
}