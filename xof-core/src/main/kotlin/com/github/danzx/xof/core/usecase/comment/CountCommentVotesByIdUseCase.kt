package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.repository.comment.CommentVotesCounter
import com.github.danzx.xof.core.usecase.UseCase

import javax.inject.Inject

@UseCaseComponent
class CountCommentVotesByIdUseCase : UseCase<Long, Long> {

    @Inject
    lateinit var counter : CommentVotesCounter

    override operator fun invoke(id: Long) = counter.countVotesById(id)
}