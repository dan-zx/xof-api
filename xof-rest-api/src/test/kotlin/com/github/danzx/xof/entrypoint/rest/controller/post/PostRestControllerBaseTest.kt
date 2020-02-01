package com.github.danzx.xof.entrypoint.rest.controller.post

import com.github.danzx.xof.core.domain.Comment
import com.github.danzx.xof.core.domain.Post
import com.github.danzx.xof.core.usecase.UseCase
import com.github.danzx.xof.core.usecase.comment.command.CommentsLoaderCommand
import com.github.danzx.xof.core.usecase.post.command.CreateNewPostCommand
import com.github.danzx.xof.core.usecase.post.command.PostsLoaderCommand
import com.github.danzx.xof.core.usecase.post.command.ReplacePostContentCommand
import com.github.danzx.xof.core.usecase.post.command.ReplacePostTitleCommand
import com.github.danzx.xof.core.util.Page
import com.github.danzx.xof.entrypoint.rest.controller.BaseRestController
import com.github.danzx.xof.entrypoint.rest.controller.PostRestController
import com.github.danzx.xof.entrypoint.rest.controller.SpringRestControllerTest

import com.ninjasquad.springmockk.MockkBean

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest

@WebMvcTest(PostRestController::class)
abstract class PostRestControllerBaseTest : SpringRestControllerTest() {

    @MockkBean(name="createNewPostUseCase")
    lateinit var createNewPostUseCase: UseCase<CreateNewPostCommand, Post>

    @MockkBean(name="getPostByIdUseCase")
    lateinit var getPostByIdUseCase: UseCase<Long, Post>

    @MockkBean(name="getPostsUseCase")
    lateinit var getPostsUseCase: UseCase<PostsLoaderCommand, Page<Post>>

    @MockkBean(name="replacePostTitleUseCase")
    lateinit var replacePostTitleUseCase: UseCase<ReplacePostTitleCommand, Post>

    @MockkBean(name="replacePostContentUseCase")
    lateinit var replacePostContentUseCase: UseCase<ReplacePostContentCommand, Post>

    @MockkBean(name="deletePostByIdUseCase")
    lateinit var deletePostByIdUseCase: UseCase<Long, Unit>

    @MockkBean(name="getCommentsUseCase")
    lateinit var getCommentsUseCase: UseCase<CommentsLoaderCommand, Page<Comment>>

    companion object {
        @JvmStatic
        protected val BASE_PATH = "${BaseRestController.BASE_PATH}/posts"
    }
}