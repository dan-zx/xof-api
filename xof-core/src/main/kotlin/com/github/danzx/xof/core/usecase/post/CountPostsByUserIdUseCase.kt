package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.repository.post.PostsByUserIdCounter

import javax.inject.Inject

@UseCaseComponent
class CountPostsByUserIdUseCase : UseCase<Long, Long> {

    @Inject
    lateinit var counter: PostsByUserIdCounter

    override operator fun invoke(userId: Long) = counter.countByUserId(userId)
}