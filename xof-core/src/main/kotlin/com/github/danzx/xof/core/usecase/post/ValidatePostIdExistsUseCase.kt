package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.exception.PostNotFoundException
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.post.PostIdChecker

import javax.inject.Inject

class ValidatePostIdExistsUseCase : UseCase<Long, Unit> {

    @Inject lateinit var checker: PostIdChecker

    override operator fun invoke(id: Long) {
        if (!checker.existsId(id)) throw PostNotFoundException()
    }
}