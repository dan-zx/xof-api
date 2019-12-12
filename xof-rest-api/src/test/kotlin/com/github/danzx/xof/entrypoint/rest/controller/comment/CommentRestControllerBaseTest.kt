package com.github.danzx.xof.entrypoint.rest.controller.comment

import com.github.danzx.xof.core.usecase.UseCaseExecutor
import com.github.danzx.xof.core.usecase.comment.CreateNewCommentUseCase
import com.github.danzx.xof.core.usecase.comment.DeleteCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentByIdUseCase
import com.github.danzx.xof.core.usecase.comment.GetCommentsUseCase
import com.github.danzx.xof.core.usecase.comment.ReplaceCommentContentUseCase
import com.github.danzx.xof.core.usecase.comment.VoteOnCommentUseCase
import com.github.danzx.xof.entrypoint.rest.controller.CommentRestController
import com.github.danzx.xof.entrypoint.rest.controller.SpringRestControllerTest

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(CommentRestController::class)
abstract class CommentRestControllerBaseTest : SpringRestControllerTest() {

    protected val basePath = "/comments"

    @MockkBean protected lateinit var createNewCommentUseCase: CreateNewCommentUseCase
    @MockkBean protected lateinit var getCommentByIdUseCase: GetCommentByIdUseCase
    @MockkBean protected lateinit var getCommentsUseCase: GetCommentsUseCase
    @MockkBean protected lateinit var replaceCommentContentUseCase: ReplaceCommentContentUseCase
    @MockkBean protected lateinit var voteOnCommentUseCase: VoteOnCommentUseCase
    @MockkBean protected lateinit var deleteCommentByIdUseCase: DeleteCommentByIdUseCase
    @SpykBean  protected lateinit var useCaseExecutor: UseCaseExecutor
}