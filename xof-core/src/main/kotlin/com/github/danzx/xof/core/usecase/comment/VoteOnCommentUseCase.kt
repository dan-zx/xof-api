package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.dataprovider.comment.CommentVotePersister
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import javax.inject.Inject

class VoteOnCommentUseCase : UseCase<Vote, Unit> {

    @Inject lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase
    @Inject lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @Inject lateinit var commentVotePersister : CommentVotePersister

    override operator fun invoke(vote: Vote) {
        validateCommentIdExistsUseCase(vote.entityId)
        validateUserIdExistsUseCase(vote.userId)
        commentVotePersister.saveOrUpdate(vote)
    }
}