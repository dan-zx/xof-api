package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.dataprovider.post.PostByIdRemover

import javax.inject.Inject

@UseCaseComponent
class DeletePostByIdUseCase : UseCase<Long, Unit> {

    @Inject
    lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase

    @Inject
    lateinit var remover: PostByIdRemover

    override operator fun invoke(id: Long) {
        validatePostIdExistsUseCase(id)
        remover.removeById(id)
    }
}