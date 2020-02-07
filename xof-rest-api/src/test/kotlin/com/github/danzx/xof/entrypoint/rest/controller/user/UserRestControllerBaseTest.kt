package com.github.danzx.xof.entrypoint.rest.controller.user

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.domain.User
import com.github.danzx.xof.core.domain.Vote
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.user.command.CreateNewUserCommand
import com.github.danzx.xof.core.usecase.user.command.ReplaceUserCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.controller.BaseRestController
import com.github.danzx.xof.entrypoint.rest.controller.SpringRestControllerTest
import com.github.danzx.xof.entrypoint.rest.controller.UserRestController

import com.ninjasquad.springmockk.MockkBean

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(UserRestController::class)
abstract class UserRestControllerBaseTest : SpringRestControllerTest() {

    @MockkBean(name="createNewUserUseCase")
    lateinit var createNewUserUseCase: UseCase<CreateNewUserCommand, User>

    @MockkBean(name="getUserByIdUseCase")
    lateinit var getUserByIdUseCase: UseCase<Long, User>

    @MockkBean(name="getUserByUsernameUseCase")
    lateinit var getUserByUsernameUseCase: UseCase<String, User>

    @MockkBean(name="replaceUserUseCase")
    lateinit var replaceUserUseCase: UseCase<ReplaceUserCommand, User>

    @MockkBean(name="deleteUserByIdUseCase")
    lateinit var deleteUserByIdUseCase: UseCase<Long, Unit>

    @MockkBean(name="getPostsUseCase")
    lateinit var getPostsUseCase: UseCase<PostsLoaderCommand, Page<Post>>

    @MockkBean(name="getCommentsUseCase")
    lateinit var getCommentsUseCase: UseCase<CommentsLoaderCommand, Page<Comment>>

    @MockkBean(name="voteOnPostUseCase")
    lateinit var voteOnPostUseCase: UseCase<Vote, Unit>

    @MockkBean(name="voteOnCommentUseCase")
    lateinit var voteOnCommentUseCase: UseCase<Vote, Unit>

    companion object {
        @JvmStatic
        protected val BASE_PATH = "${BaseRestController.BASE_PATH}/users"
    }
}