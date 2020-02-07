package com.github.danzx.xof.core.usecase.post

import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.post.command.ReplacePostTitleCommand

class ReplacePostTitleUseCase : ReplacePostDataUseCase(), UseCase<ReplacePostTitleCommand, Post> {

    override operator fun invoke(command: ReplacePostTitleCommand) = changePostDataWithId(command.id) { title = command.value }
}