package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.post.command.ReplacePostContentCommand

@UseCaseComponent
class ReplacePostContentUseCase : ReplacePostDataUseCase(), UseCase<ReplacePostContentCommand, Post> {

    override operator fun invoke(command: ReplacePostContentCommand) = changePostDataWithId(command.id) { content = command.value }
}