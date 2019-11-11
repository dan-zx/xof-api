package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.Post

import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.post.mapper.toNewPost
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase
import com.github.danzx.xof.core.dataprovider.post.PostPersister

import javax.inject.Inject

@UseCaseComponent
class CreateNewPostUseCase : UseCase<CreateNewPostCommand, Post> {

    @Inject
    lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase

    @Inject
    lateinit var persister: PostPersister

    override operator fun invoke(command: CreateNewPostCommand) : Post {
        validateUserIdExistsUseCase(command.userId)
        return persister.save(command.toNewPost())
    }
}