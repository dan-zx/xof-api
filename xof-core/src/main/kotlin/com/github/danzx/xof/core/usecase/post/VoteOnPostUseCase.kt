package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.post.PostVotePersister
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import javax.inject.Inject

class VoteOnPostUseCase : UseCase<Vote, Unit> {

    @Inject lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase
    @Inject lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase
    @Inject lateinit var postVotePersister : PostVotePersister

    override operator fun invoke(vote: Vote) {
        validatePostIdExistsUseCase(vote.entityId)
        validateUserIdExistsUseCase(vote.userId)
        postVotePersister.saveOrUpdate(vote)
    }
}