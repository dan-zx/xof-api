package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.repository.comment.CommentUpdater
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand

import java.time.LocalDateTime.now

import javax.inject.Inject

@UseCaseComponent
class ReplaceCommentContentUseCase : UseCase<ReplaceCommentContentCommand, Comment> {

    @Inject
    lateinit var getCommentByIdUseCase: GetCommentByIdUseCase

    @Inject
    lateinit var updater: CommentUpdater

    override operator fun invoke(command: ReplaceCommentContentCommand) : Comment {
        val comment = getCommentByIdUseCase(command.id)
        comment.apply {
            content = command.value
            updated = now()
        }
        return updater.update(comment)
    }
}