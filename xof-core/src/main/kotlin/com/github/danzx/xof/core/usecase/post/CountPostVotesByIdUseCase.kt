package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.repository.post.PostVotesCounter

import javax.inject.Inject

@UseCaseComponent
class CountPostVotesByIdUseCase : UseCase<Long, Long> {

    @Inject
    lateinit var counter: PostVotesCounter

    override operator fun invoke(id: Long) = counter.countVotesById(id)
}