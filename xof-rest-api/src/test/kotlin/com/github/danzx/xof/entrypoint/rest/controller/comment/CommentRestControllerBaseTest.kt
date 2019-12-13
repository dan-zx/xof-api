package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.UseCaseExecutor
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.comment.command.CreateNewCommentCommand
import com.github.danzx.xof.core.usecase.comment.command.ReplaceCommentContentCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.controller.CommentRestController
import com.github.danzx.xof.entrypoint.rest.controller.SpringRestControllerTest

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(CommentRestController::class)
abstract class CommentRestControllerBaseTest : SpringRestControllerTest() {

    protected val basePath = "/api/comments"

    @MockkBean(name="createNewCommentUseCase")
    lateinit var createNewCommentUseCase: UseCase<CreateNewCommentCommand, Comment>

    @MockkBean(name="getCommentByIdUseCase")
    lateinit var getCommentByIdUseCase: UseCase<Long, Comment>

    @MockkBean(name="getCommentsUseCase")
    lateinit var getCommentsUseCase: UseCase<CommentsLoaderCommand, Page<Comment>>

    @MockkBean(name="replaceCommentContentUseCase")
    lateinit var replaceCommentContentUseCase: UseCase<ReplaceCommentContentCommand, Comment>

    @MockkBean(name="voteOnCommentUseCase")
    lateinit var voteOnCommentUseCase: UseCase<Vote, Unit>

    @MockkBean(name="deleteCommentByIdUseCase")
    lateinit var deleteCommentByIdUseCase: UseCase<Long, Unit>

    @SpykBean protected lateinit var useCaseExecutor: UseCaseExecutor
}