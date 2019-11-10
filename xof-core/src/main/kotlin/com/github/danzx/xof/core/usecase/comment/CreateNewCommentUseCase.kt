package com.github.danzx.xof.core.usecase.comment

import com.github.danzx.xof.core.annotation.UseCaseComponent
import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.repository.comment.CommentPersister
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.comment.mapper.toNewComment
import com.github.danzx.xof.core.usecase.post.ValidatePostIdExistsUseCase
import com.github.danzx.xof.core.usecase.user.ValidateUserIdExistsUseCase

import javax.inject.Inject

@UseCaseComponent
class CreateNewCommentUseCase : UseCase<CreateNewCommentCommand, Comment> {

    @Inject
    lateinit var validateUserIdExistsUseCase: ValidateUserIdExistsUseCase

    @Inject
    lateinit var validatePostIdExistsUseCase: ValidatePostIdExistsUseCase

    @Inject
    lateinit var validateCommentIdExistsUseCase: ValidateCommentIdExistsUseCase

    @Inject
    lateinit var persister: CommentPersister

    override operator fun invoke(command: CreateNewCommentCommand) : Comment {
        validateUserIdExistsUseCase(command.userId)
        validatePostIdExistsUseCase(command.postId)
        if (command.parentId != null) validateCommentIdExistsUseCase(command.parentId!!)
        return persister.save(command.toNewComment())
    }
}