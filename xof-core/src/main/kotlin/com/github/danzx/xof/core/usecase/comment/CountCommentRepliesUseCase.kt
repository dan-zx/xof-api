package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.repository.comment.CommentReplayCounter
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

@UseCaseComponent
class CountCommentRepliesUseCase : UseCase<Long, Long> {

    @Inject
    lateinit var counter : CommentReplayCounter

    override operator fun invoke(id: Long) = counter.countRepliesById(id)
}